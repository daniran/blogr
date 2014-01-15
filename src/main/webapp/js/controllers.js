'use strict';

/* Controllers */
var controllers = angular.module('blogrApp.controllers', []);

// List
controllers.controller('BlogListController', ['$scope', '$http', '$route', 'Post', 'POST_FETCH_LIMIT',
    function ($scope, $http, $route, Post, POST_FETCH_LIMIT) {

        $scope.myVar = 15 + Date.now();

        $scope.posts = Post.query({limit: POST_FETCH_LIMIT});


    }]
);

// Read
controllers.controller('BlogReadController', ['$scope', '$http', '$route', 'Post',
    function ($scope, $http, $route, Post) {
        if ($route.current.params && $route.current.params.postId)
            $scope.currentPost = Post.get({postId: $route.current.params.postId});
    }]
);

//Edit
controllers.controller('BlogEditController', ['$scope', '$http', '$route', 'Post', 'newPost',
        function ($scope, $http, $route, Post, newPost) {
            if ($route.current.params && $route.current.params.postId)
                $scope.currentPost = Post.get({postId: $route.current.params.postId});
            else
                $scope.currentPost = newPost;

            $scope.save = function () {
                Post.save($scope.currentPost);
            }
        }]
    ).factory('newPost', function () {
        return {
            title: 'new title',
            content: 'new content'
        }
    });