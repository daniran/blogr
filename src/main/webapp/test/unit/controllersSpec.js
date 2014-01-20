'use strict';

describe('test controllers', function () {
    var $httpBackend, $rootScope, $location, $route, createController, POST_FETCH_LIMIT;

    beforeEach(module('blogrApp'));

    it('initialize injections', inject(function ($injector) {
        // Set up the mock http service responses
        $httpBackend = $injector.get('$httpBackend');
        POST_FETCH_LIMIT = $injector.get('POST_FETCH_LIMIT');

        // Get hold of a scope (i.e. the root scope)
        $rootScope = $injector.get('$rootScope');

        // The $controller service is used to create instances of controllers
        var $controller = $injector.get('$controller');

        createController = function (name) {
            return $controller(name, {'$scope': $rootScope });
        };

        $httpBackend.when('GET', 'partials/list.html').respond("<html><body></body></html>");
        $httpBackend.expectGET('partials/list.html');
        // route service
        $location = $injector.get('$location');
        $route = $injector.get('$route');
        $httpBackend.flush();
    }));

    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    describe("test BlogListController controller", function () {

        it('should fetch post list', function () {
            // GET ALL
            var fetchPostsUrl = 'api/posts?limit=' + POST_FETCH_LIMIT;
            $httpBackend.when('GET', fetchPostsUrl).respond([
                {
                    id: 1,
                    title: "title 1"
                },
                {
                    id: 2,
                    title: "title 2"
                }
            ]);
            $httpBackend.expectGET(fetchPostsUrl);
            var controller = createController("BlogListController");
            $httpBackend.flush();
            expect($rootScope.posts.length).toBe(2);
            expect($rootScope.posts[1].title).toBe("title 2");
        });
    });
    describe("test BlogReadController controller", function () {

        it('should fetch single post', function () {
            // GET
            var fetchPostUrl = 'api/posts/2';
            $httpBackend.when('GET', fetchPostUrl).respond(
                {
                    id: 2,
                    title: "title 2"
                }
            );
            $httpBackend.when('GET', 'partials/read.html').respond("<html><body></body></html>");
            $httpBackend.expectGET('partials/read.html');
            $httpBackend.expectGET(fetchPostUrl);
            $location.path("/read/2");
            $rootScope.$digest();

            var controller = createController("BlogReadController");
            $httpBackend.flush();
            expect($rootScope.post).toBeDefined();
            expect($rootScope.post.title).toBe("title 2");
        });

    });

});
