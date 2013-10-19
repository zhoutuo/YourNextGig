'use strict';

var controllers = angular.module('yng.controllers', []);
controllers.controller('searchCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
	// declare variables
	$scope.showLoader = '';
	// grab geo info based on IP from city-check
	$http.get('https://city-check.appspot.com/json')
					.success(function(response) {
						$scope.city = response.human_city;
						$scope.geocode = response.city_latlng;
					})
					.error(function() {
						alert("cannot get the your current city");
					})
					.then(function() {
						// complete event for jsonp
						$scope.showLoader = 'hide';
					});
	$scope.submitLocation = function() {
		// go to concert page
		$location.path("/concert").search({
			geocode: $scope.geocode
		});
	}
}]);
controllers.controller('concertCtrl', ['$scope', function($scope) {

}]);