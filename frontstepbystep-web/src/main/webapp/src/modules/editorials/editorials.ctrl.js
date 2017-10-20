(function (ng) {
    var mod = ng.module("editorialModule");
    mod.constant("editorialContext", "api/editorials");
    mod.controller('editorialCtrl', ['$scope', '$http', 'editorialContext', '$state',
        function ($scope, $http, editorialContext, $state) {
            $http.get(editorialContext).then(function (response) {
                $scope.editorialsRecords = response.data;
            });

            if ($state.params.editorialsId !== undefined) {
                $http.get(editorialContext + '/' + $state.params.editorialsId).then(function (response) {
                    $scope.booksRecords = response.data.books;
                    $scope.currentEditorial = response.data;
                });
            }
        }
    ]);
}
)(angular);