(function (ng) {
    var mod = ng.module("reviewModule", ['bookModule', 'ui.router']);
    mod.constant("reviewsContext", "reviews");
    mod.constant("booksContext", "api/books");

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/reviews/';
            $urlRouterProvider.otherwise("/reviewsList");

            $stateProvider.state('reviews', {
                url: '/reviews',
                abstract: true,
                parent: 'bookDetail',
                views: {
                    childrenView: {
                        templateUrl: basePath + 'reviews.html'
                    }
                }
            }).state('reviewsList', {
                url: '/list',
                parent: 'reviews',
                views: {
                    'listView': {
                        templateUrl: basePath + 'reviews.list.html',
                        controller: 'reviewsCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }]);
})(window.angular);