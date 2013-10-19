'use strict';

/* Directives */


angular.module('yng.directives', []).
  directive('timeline', [function() {
  	return {
  		restrict: 'E',
  		templateUrl: "partials/timeline.html",
  		scope: true,
  		link: function(scope, element, attrs) {
  			// init timeline
			createStoryJS({
				type: 'timeline',
				width: '100%',
				height: '600',
				source: 'example_json.json',
				embed_id: 'timelineContent'
			});
  		}
  	}
 }]);
