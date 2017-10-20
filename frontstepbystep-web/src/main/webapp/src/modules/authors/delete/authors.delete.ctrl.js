(function (ng) {
    var mod = ng.module("authorModule");
    mod.constant("authorsContext", "api/authors");
    mod.controller('authorDeleteCtrl', ['$scope', '$http', 'authorsContext', '$state',
        function ($scope, $http, authorsContext, $state) {
            var idAuthor = $state.params.authorId;
            $scope.deleteAuthor = function () {
                $http.delete(authorsContext + '/' + idAuthor, {}).then(function (response) {
                    $state.go('authorsList', {authorId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(angular);