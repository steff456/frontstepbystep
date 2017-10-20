(function (ng) {
    var mod = ng.module("reviewModule");
    mod.constant("reviewsContext", "reviews");
    mod.constant("booksContext", "api/books");
    mod.controller('reviewsCtrl', ['$scope', '$http', 'booksContext', '$state', 'reviewsContext',
        function ($scope, $http, booksContext, $state, reviewsContext) {
            $http.get(booksContext + '/' + $state.params.bookId + '/' + reviewsContext).then(function (response) {
                $scope.reviewsRecords = response.data;
            });
        }
    ]);
}
)(angular);