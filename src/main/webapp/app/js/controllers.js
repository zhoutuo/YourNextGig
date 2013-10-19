'use strict';

angular.module('yng.controllers', []).
controller('searchCtrl', ['$scope', '$http', function($scope, $http) {
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

}]);