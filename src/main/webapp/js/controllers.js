'use strict';

/* Controllers */
var controllers = angular.module('blogrApp.controllers', []);

// List
controllers.controller('BlogListController', ['$scope', '$http', '$route', 'PostsService', 'creoleParser', 'POST_FETCH_LIMIT',
    function ($scope, $http, $route, PostsService, creoleParser, POST_FETCH_LIMIT) {
        $scope.posts = PostsService.query({limit: POST_FETCH_LIMIT});
    }]
);

// Read
controllers.controller('BlogReadController', ['$scope', '$http', '$route', 'PostsService',
    function ($scope, $http, $route, PostsService) {
        if ($route.current.params && $route.current.params.postId) {
            $scope.post = PostsService.get({postId: $route.current.params.postId});
        }
    }]
);

//Edit
controllers.controller('BlogEditController', ['$scope', '$http', '$route', 'PostsService', 'newPost',
        function ($scope, $http, $route, PostsService, newPost) {
            if ($route.current.params && $route.current.params.postId)
                $scope.post = PostsService.get({postId: $route.current.params.postId});
            else
                $scope.post = newPost;

            $scope.save = function () {
                PostsService.save($scope.post);
            }
        }]
    ).factory('newPost', function () {
        return {
            title: 'new title',
            content: 'new content'
        }
    });