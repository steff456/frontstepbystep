(function (ng) {
    var mod = ng.module("authorModule");
    mod.constant("authorsContext", "api/authors");
    mod.controller('authorCtrl', ['$scope', '$http', 'authorsContext', '$state',
        function ($scope, $http, authorsContext, $state) {
            $http.get(authorsContext).then(function (response) {
                $scope.authorsRecords = response.data;
            });

            if ($state.params.authorId !== undefined) {
                $http.get(authorsContext + '/' + $state.params.authorId).then(function (response) {
                    $scope.booksRecords = response.data.books;
                    $scope.currentAuthor = response.data;
                });
            }
        }
    ]);
}
)(angular);