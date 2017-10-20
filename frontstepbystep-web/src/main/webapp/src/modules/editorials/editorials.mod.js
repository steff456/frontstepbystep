(function (ng) {
    // Definición del módulo
    var mod = ng.module("editorialModule", ['ui.router']);

    // Configuración de los estados del módulo
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            // En basePath se encuentran los templates y controladores de módulo
            var basePath = 'src/modules/editorials/';
            // Mostrar la lista de editoriales será el estado por defecto del módulo
            $urlRouterProvider.otherwise("/editorialsList");
            // Definición del estado 'editorialsList' donde se listan los editoriales
            $stateProvider.state('editorialsList', {
                // Url que aparecerá en el browser
                url: '/editorials/list',
                views: {
                    'mainView': {
                        templateUrl: basePath + 'editorials.list.html',
                        controller: 'editorialCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }
    ]);
})(window.angular);
