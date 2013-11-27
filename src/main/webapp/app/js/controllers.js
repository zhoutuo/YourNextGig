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
				console.log(response.region_name);
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
controllers.controller('concertsCtrl', ['$scope', '$http', '$location', '$timeout',
	function($scope, $http, $location, $timeout) {
		var testresponse = [{
			"id": "18581349",
			"artists": [{
				"id": "05517043-ff78-4988-9c22-88c68588ebb9",
				"name": "Paul Simon",
				"info": "http://en.wikipedia.org/wiki?curid=50745"
			}],
			"dtstart": "Sat Feb 15 00:00:00 PST 2014",
			"dtend": null,
			"name": "Paul Simon and Sting at The Forum (February 15, 2014)",
			"venue": {
				"id": "16272",
				"location": {
					"geo": {
						"additionalProperties": {

						},
						"longitude": -118.3405316,
						"latitude": 33.9582047
					},
					"additionalProperties": {

					},
					"state": "CA",
					"city": null,
					"country": null
				},
				"additionalProperties": {

				},
				"name": "The Forum",
				"info": null
			},
			"info": "http://www.songkick.com/concerts/18581349-paul-simon-at-forum?utm_source=23184&utm_medium=partner"
		}, {
			"id": "18521894",
			"artists": [{
				"id": "164f0d73-1234-4e2c-8743-d77bf2191051",
				"name": "Kanye West",
				"info": "http://en.wikipedia.org/wiki?curid=523032"
			}],
			"dtstart": "Fri Dec 13 00:00:00 PST 2013",
			"dtend": null,
			"additionalProperties": {

			},
			"name": "Kanye West at Honda Center (December 13, 2013)",
			"venue": {
				"id": "6787",
				"location": {
					"geo": {
						"additionalProperties": {

						},
						"longitude": -117.876522,
						"latitude": 33.807723
					},
					"additionalProperties": {

					},
					"state": "CA",
					"city": null,
					"country": null
				},
				"additionalProperties": {

				},
				"name": "Honda Center",
				"info": null
			},
			"info": "http://www.songkick.com/concerts/18521894-kanye-west-at-honda-center?utm_source=23184&utm_medium=partner"
		}, {
			"id": "18683899",
			"artists": [{
				"id": "ee190f6b-7d98-43ec-b924-da5f8018eca0",
				"name": "Janelle Monae",
				"info": "http://en.wikipedia.org/wiki?curid=13828397"
			}],
			"dtstart": "Tue Jan 14 00:00:00 PST 2014",
			"dtend": null,
			"name": "Janelle Monáe at House of Blues (January 14, 2014)",
			"venue": {
				"id": "38120",
				"location": {
					"geo": {
						"additionalProperties": {

						},
						"longitude": -117.9229975,
						"latitude": 33.8095868
					},
					"additionalProperties": {

					},
					"state": "CA",
					"city": null,
					"country": null
				},
				"additionalProperties": {

				},
				"name": "House of Blues",
				"info": null
			},
			"info": "http://www.songkick.com/concerts/18683899-janelle-monae-at-house-of-blues?utm_source=23184&utm_medium=partner"
		}, {
			"id": "18637324",
			"artists": [{
				"id": "52074ba6-e495-4ef3-9bb4-0703888a9f68",
				"name": "Arcade Fire",
				"info": "http://en.wikipedia.org/wiki?curid=1098713"
			}, {
				"id": "7808accb-6395-4b25-858c-678bbb73896b",
				"name": "Bastille",
				"info": "http://en.wikipedia.org/wiki?curid=36888630"
			}, {
				"id": "119a2c67-e6d6-41cf-a58c-c51b75c80daa",
				"name": "Capital Cities",
				"info": "http://en.wikipedia.org/wiki?curid=37759538"
			}, {
				"id": "8d455809-96b3-4bb6-8829-ea4beb580d35",
				"name": "Phoenix",
				"info": "http://en.wikipedia.org/wiki?curid=2282994"
			}, {
				"id": "3599a39e-4e10-4cb5-90d4-c8a015ebc73b",
				"name": "Portugal. The Man",
				"info": "http://en.wikipedia.org/wiki?curid=5497650"
			}],
			"dtstart": "Sun Dec 08 00:00:00 PST 2013",
			"dtend": null,
			"additionalProperties": {

			},
			"name": "KROQ Almost Acoustic Christmas 2013",
			"venue": {
				"id": "3034",
				"location": {
					"geo": {
						"additionalProperties": {

						},
						"longitude": -118.2813035,
						"latitude": 34.0225788
					},
					"additionalProperties": {

					},
					"state": "CA",
					"city": null,
					"country": null
				},
				"additionalProperties": {

				},
				"name": "Shrine Auditorium",
				"info": null
			},
			"info": "http://www.songkick.com/festivals/6351/id/18637324-kroq-almost-acoustic-christmas-2013?utm_source=23184&utm_medium=partner"
		}];
		var timelineJson = {
			"timeline": {
				"headline": "Los Angeles",
				"type": "default",
				"text": "We found " + testresponse.length + " upcoming popular concerts",
				"asset": {
					"media": "http://maps.googleapis.com/maps/api/staticmap?center=Los+Angeles,CA&zoom=14&size=400x400&sensor=false"
				},
				"date": []
			}
		}

		for (var i = 0; i < testresponse.length; ++i) {
			var concert = testresponse[i];
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

		$timeout(function() {
			$scope.$broadcast('showTimeline', timelineJson);
		}, 100);
	}
]);
controllers.controller('artistCtrl', ['$scope', '$http', '$location', '$timeout',
	function($scope, $http, $location, $timeout) {
		var testresponse = {
			"id": "05517043-ff78-4988-9c22-88c68588ebb9",
			"awards": [{
				"name": "Grammy Award for Album of the Year",
				"date": "Wed Jan 01 00:00:00 PST 1975"
			}, {
				"name": "Grammy Award for Record of the Year",
				"date": "Thu Jan 01 00:00:00 PST 1987"
			}, {
				"name": "Grammy Award for Best Male Pop Vocal Performance",
				"date": "Wed Jan 01 00:00:00 PST 1975"
			}, {
				"name": "Grammy Award for Record of the Year",
				"date": "Mon Jan 01 00:00:00 PST 1968"
			}, {
				"name": "Kennedy Center Honors",
				"date": "Tue Jan 01 00:00:00 PST 2002"
			}, {
				"name": "Grammy Award for Record of the Year",
				"date": "Thu Jan 01 00:00:00 PST 1970"
			}, {
				"name": "Grammy Award for Album of the Year",
				"date": "Thu Jan 01 00:00:00 PST 1970"
			}, {
				"name": "Grammy Award for Song of the Year",
				"date": "Thu Jan 01 00:00:00 PST 1970"
			}, {
				"name": "Grammy Award for Best Instrumental Arrangement Accompanying Vocalist(s)",
				"date": "Thu Jan 01 00:00:00 PST 1970"
			}, {
				"name": "Grammy Award for Best Contemporary Song",
				"date": "Thu Jan 01 00:00:00 PST 1970"
			}, {
				"name": "Grammy Award for Best Score Soundtrack for Visual Media",
				"date": "Mon Jan 01 00:00:00 PST 1968"
			}, {
				"name": "Grammy Award for Album of the Year",
				"date": "Wed Jan 01 00:00:00 PST 1986"
			}, {
				"name": "Primetime Emmy Award for Outstanding Writing In A Comedy-Variety Or Music Special",
				"date": "Sun Jan 01 00:00:00 PST 1978"
			}, {
				"name": "Brit Award for Best International Solo Artist",
				"date": "Thu Jan 01 00:00:00 PST 1987"
			}],
			"albums": [{
				"id": "http://rdf.freebase.com/ns/m.0g59_v_",
				"releaseDate": "Sat Jan 01 00:00:00 PST 2011",
				"name": "So Beautiful or So What",
				"rating": "85"
			}, {
				"id": "http://rdf.freebase.com/ns/m.0cy8dc",
				"releaseDate": "Sun Jan 01 00:00:00 PST 2006",
				"name": "Surprise",
				"rating": "78"
			}, {
				"id": "http://rdf.freebase.com/ns/m.01j2kkn",
				"releaseDate": "Sat Jan 01 00:00:00 PST 2000",
				"name": "You're the One",
				"rating": "76"
			}, {
				"id": "http://rdf.freebase.com/ns/m.0jxk41y",
				"releaseDate": "Sun Jan 01 00:00:00 PST 2012",
				"name": "Graceland: 25th Anniversary Edition",
				"rating": "98"
			}],
			"name": "Paul Simon",
			"info": "http://en.wikipedia.org/wiki?curid=50745"
		};

		var info = testresponse.info;
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
					"headline": testresponse.name,
					"text": " ",
					"type": "default",
					"asset": {
						"media": info
					},
					"date": []
				}
			};

			var albums = testresponse.albums;
			var awards = testresponse.awards;

			for (var i = albums.length - 1; i >= 0; i--) {
				var album = albums[i];
				timelineJson.timeline.date.push({
					"startDate": moment(album.releaseDate).format("MM/D/YYYY"),
					"headline": album.name
				});
			}

			for (var i = awards.length - 1; i >= 0; i--) {
				var award = awards[i];
				timelineJson.timeline.date.push({
					"startDate": moment(award.date).format("MM/D/YYYY"),
					"headline": award.name
				});
			}
			console.log(timelineJson);
			$scope.$broadcast('showTimeline', timelineJson);


		});



	}
]);
controllers.controller('reviewsCtrl', ['$scope', '$http', '$location',
	function($scope, $http, $location) {}
]);