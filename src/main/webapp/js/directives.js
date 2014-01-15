'use strict';

/* Directives */

var directives = angular.module('blogrApp.directives', []);

// parse post creole content
directives.directive('postContent', function (creoleParser) {
    return {
        restrict: "E",
        link: function postLink(scope, iElement, iAttrs) {
            if (scope.post.$promise) {
                scope.post.$promise.then(function (post) {
                    creoleParser(post.content, iElement[0]);
                });
            } else {
                creoleParser(scope.post.content, iElement[0]);
            }
        }
    }
});

// parse post date
directives.directive('postDate', function () {
    return {
        restrict: "A",
        template: ""
    }
});
