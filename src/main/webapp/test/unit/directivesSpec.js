'use strict';

/* jasmine specs for directives go here */

describe('test directives', function () {
    beforeEach(module('blogrApp.services', 'blogrApp.directives'));

    describe('test postContent directive', function () {
        it('should compile creole to HTML', function () {
            inject(function ($compile, $rootScope) {
                $rootScope.post = { content: "# one\n# two\n# three"};
                var element = $compile('<post-content></post-content>')($rootScope);
                expect(element.html()).toEqual("<ol><li> one</li>\n<li> two</li>\n<li> three</li></ol>");
            });
        });
    });

});
