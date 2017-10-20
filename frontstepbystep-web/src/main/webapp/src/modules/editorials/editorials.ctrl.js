(function (ng) {
    var mod = ng.module("editorialModule");
    mod.constant("editorialContext", "api/editorials");
    mod.controller('editorialCtrl', ['$scope', '$http', 'editorialContext',
        function ($scope, $http, editorialContext) {
            $http.get('data/editorials.json').then(function (response) {
                $scope.editorialsRecords = response.data;
            });
        }
    ]);
}
)(angular);