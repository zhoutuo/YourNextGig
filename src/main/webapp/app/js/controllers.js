'use strict';

var controllers = angular.module('yng.controllers', []);
controllers.controller('searchCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
	// declare variables
	$scope.showLoader = true;
	// grab geo info based on IP from city-check
	$http.get('https://city-check.appspot.com/json')
					.success(function(response) {
						console.log(response.region_name);
						$scope.city = response.city;
						$scope.region = response.region_name;
						$scope.location = response.human_city;
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
		$location.path("/concert").search({
			location: $scope.city + ',' + $scope.region
		});
	}
}]);
controllers.controller('concertCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
}]);