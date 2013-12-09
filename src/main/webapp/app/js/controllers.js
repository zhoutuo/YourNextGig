'use strict';

var controllers = angular.module('yng.controllers', []);
controllers.controller('searchCtrl', ['$scope', '$http', '$location',
	function($scope, $http, $location) {
		// declare variables
		$scope.showLoader = true;
		$scope.disableButton = true;
		// grab geo info based on IP from city-check
		$http.get('https://city-check.appspot.com/json')
			.success(function(response) {
				$scope.city = response.city;
				$scope.region = response.region_name;
				$scope.location = response.human_city;
				$scope.disableButton = false;
			})
			.error(function() {
				alert("cannot get the your current city");
			})
			.then(function() {
				// complete event for jsonp
				$scope.showLoader = false;
			});
		$scope.submitLocation = function() {
			// go to concert page
			$location.path("/concerts").search({
				location: $scope.city + ',' + $scope.region
			});
		}
	}
]);
controllers.controller('concertsCtrl', ['$scope', '$http', '$location',
	function($scope, $http, $location) {
		$http.get("/YourNextGig/api/concert/search", {
			params: {
				lat: 1.0,
				lon: 1.0,
				starttime: 3,
				endtime: 4
			}
		}).success(function(data, status) {
			var concerts = data;
			var timelineJson = {
				"timeline": {
					"headline": "Los Angeles",
					"type": "default",
					"text": "We found " + concerts.length + " upcoming popular concerts",
					"asset": {
						"media": "http://maps.googleapis.com/maps/api/staticmap?center=Los+Angeles,CA&zoom=14&size=400x400&sensor=false"
					},
					"date": []
				}
			}

			for (var i = 0; i < concerts.length; ++i) {
				var concert = concerts[i];
				var headline = concert.name;
				var date = moment(concert.dtstart).format("MM/D/YYYY");
				var venue = concert.venue;
				var artists = concert.artists;
				var text = "Performers: <br>";
				for (var j = 0; j < artists.length; ++j) {
					var artist = artists[j];
					var link = "<a href='#/artist?id=" + artist.id + "'>" + artist.name + "</a><br>"
					text += link;
				}
				var lon = concert.geo.longitude;
				var lat = concert.geo.latitude;
				//text += "Venue: <br>" + venue.name;
				timelineJson.timeline.date.push({
					"startDate": date,
					"headline": headline,
					"text": text,
					"asset": {
						"media": "http://maps.googleapis.com/maps/api/staticmap?center=" + lat + ',' + lon + "&zoom=14&size=400x400&sensor=false&markers=" + lat + ',' + lon
					}
				});
			}
			$scope.$broadcast('showTimeline', timelineJson);

		});
	}
]);
controllers.controller('artistCtrl', ['$scope', '$http', '$location', '$q',
		function($scope, $http, $location, $q) {
			$http.get('/YourNextGig/api/artist', {
				params: {
					id: $location.search().id
				}
			}).success(function(artist, status) {

				function getinfo(artist) {
					var deferred = $q.defer();
					var albums = artist.albums;
					var signal = 2 + albums.length;

					var info = artist.info;
					var curid = info.split('=')[1];
					var profile_link = null;


					function then() {
						--signal;
						if (signal == 0) {
							deferred.resolve(artist);
						}
					}

					$http.jsonp("http://en.wikipedia.org/w/api.php?callback=JSON_CALLBACK", {
						params: {
							action: "query",
							pageids: curid,
							format: "json"
						}
					}).success(function(data) {
						artist.info = "http://en.wikipedia.org/wiki/" + data.query.pages[curid].title.replace(' ', '_');
					}).then(then);

					$http.jsonp("http://ws.audioscrobbler.com/2.0/?callback=JSON_CALLBACK", {
						params: {
							api_key: "ff0d1870597c44a71ccb5ea4afbc0a4d",
							method: "artist.getinfo",
							artist: artist.name,
							format: 'json'
						}
					}).success(function(lastfm, status) {
						artist.profile_link = lastfm.artist.image[3]['#text'];
					}).then(then);
                                        
                                        var tmp = {};
					for (var i = albums.length - 1; i >= 0; i--) {
                                            
						$http.jsonp("http://ws.audioscrobbler.com/2.0/?callback=JSON_CALLBACK", {
							params: {
								api_key: "ff0d1870597c44a71ccb5ea4afbc0a4d",
								method: "album.getinfo",
								artist: artist.name,
								album: albums[i].name,
								format: 'json'
							}
						}).success(function(lastfm, status) {
                                                    console.log(lastfm);
                                                    for (var j = albums.length - 1; j >= 0; j--) {
                                                        if(albums[j].name === lastfm.album.name){
								albums[j].cover_link = lastfm.album.image[3]['#text'];								
                                                        }
                                                    }
						}).then(then);

					};

					return deferred.promise;
				}

				var promise = getinfo(artist);
				promise.then(function(new_artist) {
					var timelineJson = {
						"timeline": {
							"headline": "<img src='" + new_artist.profile_link + "' height='250'>",
							"text": " ",
							"type": "default",
							"asset": {
								"media": new_artist.info
							},
							"date": []
						}
					};

					var albums = new_artist.albums;
					var awards = new_artist.awards;
					var rankings = new_artist.rankings;

					for (var i = albums.length - 1; i >= 0; i--) {
						var album = albums[i];
						timelineJson.timeline.date.push({
							"startDate": moment(album.releaseDate).format("MM/D/YYYY"),
							"headline": "<a href='#/reviews?id=" + album.id + "'>" + album.name + "</a>",
							"asset": {
								"media": album.cover_link
							}
						});
					}

					for (var i = awards.length - 1; i >= 0; i--) {
						var award = awards[i];
						timelineJson.timeline.date.push({
							"startDate": moment(award.date).format("MM/D/YYYY"),
							"headline": award.name
						});
					}


					for (var i = rankings.length - 1; i >= 0; i--) {
						var ranking = rankings[i];
						timelineJson.timeline.date.push({
							"startDate": moment(ranking.year).format("MM/D/YYYY"),
							"endDate": moment(ranking.year).add('y', 1).format("MM/D/YYYY"),
							"headline": "Reached #" + ranking.ranking + " on Billboard"
						});
					};
                                        console.log(timelineJson);
					$scope.$broadcast('showTimeline', timelineJson);

				});

			});

}]);
controllers.controller('reviewsCtrl', ['$scope', '$http', '$location',
	function($scope, $http, $location) {
		$http.get('/YourNextGig/api/album', {
			params: {
				id: $location.search().id
			}
		}).success(function(data, status) {
			$scope.name = data.name;
			$scope.rating = data.rating;
			$scope.date = moment(data.releaseDate).format("MM/D/YYYY");
			$scope.reviews = data.reviews;
		});
	}
]);