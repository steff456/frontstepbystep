(function (ng) {
    var mod = ng.module("bookModule", ['ui.router']);
    mod.constant("booksContext", "api/books");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/books/';
            $urlRouterProvider.otherwise("/booksList");

            $stateProvider.state('books', {
                url: '/books',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'books.html',
                        controller: 'bookCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('booksList', {
                url: '/list',
                parent: 'books',
                views: {
                    'listView': {
                        templateUrl: basePath + 'books.list.html'
                    }
                }
            }).state('bookDetail', {
                url: '/{bookId:int}/detail',
                parent: 'books',
                param: {
                    bookId: null
                },
                views: {
                    'listView': {
                        templateUrl: basePath + 'books.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'books.detail.html',
                        controller: 'bookCtrl',
                        controllerAs: 'ctrl'
                    }

                }

            });
        }]);
})(window.angular);
