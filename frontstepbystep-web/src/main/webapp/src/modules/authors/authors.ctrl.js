(function (ng) {
    var mod = ng.module("authorModule");
    mod.constant("authorsContext", "api/authors");
    mod.controller('authorCtrl', ['$scope', '$http', 'authorsContext',
        function ($scope, $http, authorsContext) {
            $http.get('data/authors.json').then(function (response) {
                $scope.authorsRecords = response.data;
            });
        }
    ]);
}
)(angular);