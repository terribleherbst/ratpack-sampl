'use strict';

describe('Hello World example', function() {
    beforeEach(module("myApp"));

    var BookListController,scope,httpBackend;

    beforeEach(inject(function ($rootScope, $controller, $http, $httpBackend) {
        scope = $rootScope.$new();

        httpBackend = $httpBackend;
        httpBackend.when("GET", "./talk").respond([{},{},{}]);

        BookListController = $controller('BookListCtrl', {$scope: scope, $http:$http});
    }));

    it ('A Trivial test checking just if the view is set',inject(function() {
        expect(scope.view).toBe("./html/list.html")
    }));


    it("should have 3 talks", function () {
         httpBackend.flush();
        expect(scope.books.length).toBe(3);
    });

 });

