(function (ng) {
    // Definición del módulo
    var mod = ng.module("authorModule", ['ui.router']);

    // Configuración de los estados del módulo
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            // En basePath se encuentran los templates y controladores de módulo
            var basePath = 'src/modules/authors/';
            // Mostrar la lista de autores será el estado por defecto del módulo
            $urlRouterProvider.otherwise("/authorsList");
            // Definición del estado 'authorsList' donde se listan los autores
            $stateProvider.state('authorsList', {
                // Url que aparecerá en el browser
                url: '/authors/list',
                views: {
                    'mainView': {
                        templateUrl: basePath + 'authors.list.html',
                        controller: 'authorCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }
    ]);
})(window.angular);
