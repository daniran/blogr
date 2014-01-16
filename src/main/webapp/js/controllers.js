'use strict';

/* Controllers */
var controllers = angular.module('blogrApp.controllers', []);

// List
controllers.controller('BlogListController', ['$scope', 'PostsService', 'POST_FETCH_LIMIT',
    function ($scope, PostsService, POST_FETCH_LIMIT) {
        $scope.posts = PostsService.query({limit: POST_FETCH_LIMIT});
    }]
);

// Read
controllers.controller('BlogReadController', ['$scope', '$route', 'PostsService',
    function ($scope, $route, PostsService) {
        if ($route.current.params && $route.current.params.postId) {
            $scope.post = PostsService.get({postId: $route.current.params.postId});
        }
    }]
);

//Edit
controllers.controller('BlogEditController', ['$scope', '$route', '$location', 'PostsService', 'newPost',
        function ($scope, $route, $location, PostsService, newPost) {
            if ($route.current.params && $route.current.params.postId)
                $scope.post = PostsService.get({postId: $route.current.params.postId});
            else
                $scope.post = newPost;

            $scope.save = function () {
                PostsService.save($scope.post, function () {
                    $location.path('/list');
                });
            }
        }]
    ).factory('newPost', function () {
        return {
            title: 'new title',
            content: 'new content'
        }
    });