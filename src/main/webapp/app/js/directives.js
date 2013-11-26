'use strict';

angular.module('yng.directives', [])
  .directive('timeline', [
    function() {
      return {
        restrict: 'E',
        templateUrl: "partials/timeline.html",
        scope: true,
        link: function(scope, element, attrs) {
          scope.$on("showTimeline", function(event, timelinejson) {
            // init timeline
            createStoryJS({
              type: 'timeline',
              width: '100%',
              height: '600',
              source: timelinejson,
              embed_id: 'timelineContent'
            });
          });
        }
      }
    }
  ]);