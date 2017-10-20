(function (ng) {
    var mod = ng.module("bookModule");
    mod.constant("booksContext", "api/books");
    mod.controller('bookCtrl', ['$scope', '$http', 'booksContext', '$state',
        function ($scope, $http, booksContext, $state) {
            $http.get(booksContext).then(function (response) {
                $scope.booksRecords = response.data;
            });
            
            if ($state.params.bookId !== undefined) {
                $http.get(booksContext + '/' + $state.params.bookId).then(function (response) {
                    $scope.currentBook = response.data;
                });
            }
        }
    ]);
}
)(angular);