'use strict';
var app = angular.module('yng', [
	'yng.filters',
	'yng.services',
	'yng.directives',
	'yng.controllers'
]);
app.config(['$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl: 'partials/search.html',
		controller: 'searchCtrl'
	})
	.when('/concerts', {
		templateUrl: 'partials/concerts.html',
		controller: 'concertsCtrl'
	})
	.when('/artist', {
		templateUrl: 'partials/concerts.html',
		controller: 'artistCtrl'
	})
	.when('/reviews', {
		templateUrl: 'partials/reviews.html',
		controller: 'reviewsCtrl'
	})
	.otherwise({
		redirectTo: '/'
	});
}]);