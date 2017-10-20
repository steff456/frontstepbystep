(function (ng) {
    // Definición del módulo
    var mod = ng.module("bookModule", ['ui.router']);

    // Configuración de los estados del módulo
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            // En basePath se encuentran los templates y controladores de módulo
            var basePath = 'src/modules/books/';
            // Mostrar la lista de libros será el estado por defecto del módulo
            $urlRouterProvider.otherwise("/booksList");
            // Definición del estado 'booksList' donde se listan los libros
            $stateProvider.state('booksList', {
                // Url que aparecerá en el browser
                url: '/books/list',
                 views: {
                    'mainView': {
                        templateUrl: basePath + 'books.list.html',
                        controller: 'bookCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }
    ]);
})(window.angular);
