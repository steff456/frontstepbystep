(
        function (ng) {
            var mod = ng.module("authorModule");
            mod.constant("authorsContext", "api/authors");
            mod.constant("booksContext", "api/books");
            mod.controller('authorUpdateCtrl', ['$scope', '$http', 'authorsContext', '$state', 'booksContext', '$rootScope', '$filter',
                function ($scope, $http, authorsContext, $state, booksContext, $rootScope, $filter) {
                    $rootScope.edit = true;

                    var idAuthor = $state.params.authorId;

                    // Este arreglo guardara los ids de los books asociados y por asociar al autor.
                    var idsBook = [];

                    // Este arreglo mostrará los books una vez esten filtrados visualmente por lo que el autor ya tiene asociado.
                    $scope.allBooksShow = [];

                    //Consulto el autor a editar.
                    $http.get(authorsContext + '/' + idAuthor).then(function (response) {
                        var author = response.data;
                        $scope.authorName = author.name;
                        $scope.authorBirthDate = new Date(author.birthDate);
                        $scope.authorDescription = author.description;
                        $scope.authorImage = author.image;
                        $scope.allBooksAuthor = author.books;
                        $scope.mergeBooks($scope.allBooksAuthor);
                    });

                    /*
                     * Esta función añade los ids de los books que ya tiene el autor asociado.
                     * @param {type} books: Son los books que ya tiene asociado el autor.
                     * @returns {undefined}
                     */
                    $scope.mergeBooks = function (books) {
                        for (var item in books) {
                            idsBook.push("" + books[item].id);
                        }
                        $scope.getBooks(books);
                    };

                    /*
                     * Esta función recibe como param los books que tiene el autor para hacer un filtro visual con todos los books que existen.
                     * @param {type} books
                     * @returns {undefined}
                     */
                    $scope.getBooks = function (books) {
                        $http.get(booksContext).then(function (response) {
                            $scope.Allbooks = response.data;
                            $scope.booksAuthor = books;

                            var filteredBooks = $scope.Allbooks.filter(function (Allbooks) {
                                return $scope.booksAuthor.filter(function (booksAuthor) {
                                    return booksAuthor.id == Allbooks.id;
                                }).length == 0
                            });

                            $scope.allBooksShow = filteredBooks;

                        });
                    };


                    //funciones para el drag and drop de HTML5 nativo
                    $scope.allowDrop = function (ev) {
                        ev.preventDefault();
                    };

                    $scope.drag = function (ev) {
                        ev.dataTransfer.setData("text", ev.target.id);
                    };

                    $scope.dropAdd = function (ev) {
                        ev.preventDefault();
                        var data = ev.dataTransfer.getData("text");
                        ev.target.appendChild(document.getElementById(data));
                        //Cuando un book se añade al autor, se almacena su id en el array idsBook
                        idsBook.push("" + data);
                    };

                    $scope.dropDelete = function (ev) {
                        ev.preventDefault();
                        var data = ev.dataTransfer.getData("text");
                        ev.target.appendChild(document.getElementById(data));
                        //Para remover el book que no se va asociar, por eso se usa el splice que quita el id del book en el array idsBook
                        var index = idsBook.indexOf(data);
                        if (index > -1) {
                            idsBook.splice(index, 1);
                        }
                    };

                    $scope.createAuthor = function () {
                        /*Se llama a la función newBooks() para buscar cada uno de los ids de los books
                         en el array que tiene todos los books y así saber como queda la lista final de los books asociados al autor.
                         */
                        $scope.newBooks();
                        $http.put(authorsContext + "/" + idAuthor, {
                            name: $scope.authorName,
                            birthDate: $scope.authorBirthDate,
                            description: $scope.authorDescription,
                            image: $scope.authorImage
                        }).then(function (response) {
                            if (idsBook.length >= 0) {
                                $http.put(authorsContext + "/" + response.data.id + "/books", $scope.allBooksAuthor).then(function (response) {
                                });
                            }
                            //Author created successfully
                            $state.go('authorsList', {authorId: response.data.id}, {reload: true});
                        });
                    };

                    $scope.newBooks = function () {
                        $scope.allBooksAuthor = [];
                        for (var ite in idsBook) {
                            for (var all in $scope.Allbooks) {
                                if ($scope.Allbooks[all].id === parseInt(idsBook[ite])) {
                                    $scope.allBooksAuthor.push($scope.Allbooks[all]);
                                }
                            }
                        }
                    };
                }
            ]);
        }
)(angular);