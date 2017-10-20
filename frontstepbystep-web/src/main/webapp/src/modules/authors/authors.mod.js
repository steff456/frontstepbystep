(function (ng) {
    var mod = ng.module("authorModule", ['ui.router']);
    mod.constant("authorsContext", "api/authors");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/authors/';
            var basePathBooks = 'src/modules/books/';
            $urlRouterProvider.otherwise("/authorsList");

            $stateProvider.state('authors', {
                url: '/authors',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'authors.html',
                        controller: 'authorCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('authorsList', {
                url: '/list',
                parent: 'authors',
                views: {
                    'listView': {
                        templateUrl: basePath + 'authors.list.html'
                    }
                }
            }).state('authorDetail', {
                url: '/{authorId:int}/detail',
                parent: 'authors',
                param: {
                    authorId: null
                },
                views: {
                    'listView': {
                        templateUrl: basePathBooks + 'books.list.html',
                        controller: 'authorCtrl',
                        controllerAs: 'ctrl'
                    },
                    'detailView': {
                        templateUrl: basePath + 'authors.detail.html',
                        controller: 'authorCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('authorsCreate', {
                url: '/create',
                parent: 'authors',
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/authors.new.html',
                        controller: 'authorNewCtrl'
                    }
                }
            }).state('authorUpdate', {
                url: '/update/{authorId:int}',
                parent: 'authors',
                param: {
                    authorId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/authors.new.html',
                        controller: 'authorUpdateCtrl'
                    }
                }
            }).state('authorDelete', {
                url: '/delete/{authorId:int}',
                parent: 'authors',
                param: {
                    authorId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/delete/author.delete.html',
                        controller: 'authorDeleteCtrl'
                    }
                }
            });
        }]);
})(window.angular);