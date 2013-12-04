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
		$http.get("/YourNextGig/api/stubconcert/search", {
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
				var lon = venue.location.geo.longitude;
				var lat = venue.location.geo.latitude;
				text += "Venue: <br>" + venue.name;
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
controllers.controller('artistCtrl', ['$scope', '$http', '$location',
	function($scope, $http, $location) {

		$http.get('/YourNextGig/api/stubartist', {
			params: {
				id: 1
			}
		}).success(function(artist, status) {
			var info = artist.info;
			var curid = info.split('=')[1];

			$http.jsonp("http://en.wikipedia.org/w/api.php?callback=JSON_CALLBACK", {
				params: {
					action: "query",
					pageids: curid,
					format: "json"
				}
			}).success(function(data) {
				info = "http://en.wikipedia.org/wiki/" + data.query.pages[curid].title.replace(' ', '_');
				var timelineJson = {
					"timeline": {
						"headline": artist.name,
						"text": " ",
						"type": "default",
						"asset": {
							"media": info
						},
						"date": []
					}
				};

				var albums = artist.albums;
				var awards = artist.awards;

				for (var i = albums.length - 1; i >= 0; i--) {
					var album = albums[i];
					timelineJson.timeline.date.push({
						"startDate": moment(album.releaseDate).format("MM/D/YYYY"),
						"headline": "<a href='#/reviews?id=" + album.id + "'>" + album.name + "</a>"
					});
				}

				for (var i = awards.length - 1; i >= 0; i--) {
					var award = awards[i];
					timelineJson.timeline.date.push({
						"startDate": moment(award.date).format("MM/D/YYYY"),
						"headline": award.name
					});
				}	
				$scope.$broadcast('showTimeline', timelineJson);

			});



		});



	}
]);
controllers.controller('reviewsCtrl', ['$scope', '$http', '$location',
	function($scope, $http, $location) {
		$http.get('/YourNextGig/api/stubalbum', {
			params: {
				id: 1
			}
		}).success(function(data, status) {
			$scope.name = data.name;
			$scope.rating = data.rating;
			$scope.date = moment(data.releaseDate).format("MM/D/YYYY");
			$scope.reviews = data.reviews;
			console.log(data.reviews);
		});
	}
]);