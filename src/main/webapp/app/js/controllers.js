'use strict';

angular.module('yng.controllers', []).
controller('searchCtrl', ['$scope', '$http', function($scope, $http) {
	// declare variables
	$scope.showLoader = '';
	// grab geo info based on IP from ipinfo.io
	$http.jsonp('http://ipinfo.io?callback=JSON_CALLBACK')
					.success(function(response) {
						$scope.city = response.city + ", " + response.region;
						$scope.geocode = response.loc;
					})
					.error(function() {
						alert("cannot get the your current city");
					})
					.then(function() {
						// complete event for jsonp
						$scope.showLoader = 'hide';
					});

}]);