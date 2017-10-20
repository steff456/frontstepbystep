(function (ng) {
    var mod = ng.module("bookModule");
    mod.constant("booksContext", "api/books");
    mod.controller('bookCtrl', ['$scope', '$http', 'booksContext',
        function ($scope, $http, booksContext) {
            $http.get('data/books.json').then(function (response) {
                $scope.booksRecords = response.data;
            });
        }
    ]);
}
)(angular);