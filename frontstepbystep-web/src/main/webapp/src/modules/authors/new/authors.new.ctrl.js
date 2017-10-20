(function (ng) {
    var mod = ng.module("authorModule");
    mod.constant("authorsContext", "api/authors");
    mod.controller('authorNewCtrl', ['$scope', '$http', 'authorsContext', '$state', 'booksContext', '$rootScope',
        function ($scope, $http, authorsContext, $state, booksContext, $rootScope) {
            $rootScope.edit = false;
            $scope.createAuthor = function () {
                $http.post(authorsContext, {
                    name: $scope.authorName,
                    birthDate: $scope.authorBirthDate,
                    description: $scope.authorDescription,
                    image: $scope.authorImage
                }).then(function (response) {
                    //Author created successfully
                    $state.go('authorsList', {authorId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(angular);