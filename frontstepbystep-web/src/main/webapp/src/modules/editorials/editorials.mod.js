(function (ng) {
    var mod = ng.module("editorialModule", ['ui.router']);
    mod.constant("editorialsContext", "api/editorials");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/editorials/';
            var basePathBooks = 'src/modules/books/';
            $urlRouterProvider.otherwise("/editorialsList");
            $stateProvider.state('editorials', {
                url: '/editorials',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'editorials.html',
                        controller: 'editorialCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('editorialsList', {
                url: '/list',
                parent: 'editorials',
                views: {
                    'listView': {
                        templateUrl: basePath + 'editorials.list.html'
                    }
                }
            }).state('editorialDetail', {
                url: '/{editorialsId:int}/detail',
                parent: 'editorials',
                param: {
                    editorialsId: null
                },
                views: {
                    'listView': {
                        templateUrl: basePathBooks + 'books.list.html',
                        controller: 'editorialCtrl',
                        controllerAs: 'ctrl'
                    },
                    'detailView': {
                        templateUrl: basePath + 'editorials.detail.html',
                        controller: 'editorialCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }]);
})(window.angular);
