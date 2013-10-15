'use strict';
var app = angular.module('yng', [
	'yng.filters',
	'yng.services',
	'yng.directives',
	'yng.controllers'
]);
app.config(['$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/home', {
		templateUrl: 'partials/search.html',
		controller: 'searchCtrl'
	})
	.otherwise({
		redirectTo: '/home'
	});
}]);