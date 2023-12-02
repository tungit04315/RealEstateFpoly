const app = angular.module("myapp", []);

app.run(function($rootScope, $http, $window) {
    const MAX_TITLE_LENGTH = 50;

    // Hàm xử lý tiêu đề
    function handleTitle(title) {
        if (title.length > MAX_TITLE_LENGTH) {
            return title.substr(0, MAX_TITLE_LENGTH) + "...";
        } else {
            return title;
        }
    };

    // Hàm tính thời gian lưu bài viết
    function getDaysSinceSave(saveTime) {
        const now = new Date();

        const diff = now - new Date(saveTime);
        console.log(diff);
        return Math.floor(diff / (1000 * 60 * 60 * 24));
    };

    $rootScope.listPostLike = function() {
        $http.get(`/likes`).then(response => {
            if (response.data) {
                $rootScope.likes = response.data;
                $rootScope.likes.forEach(like => {
                    like.post_id.post_title = handleTitle(like.post_id.post_title);
                    like.likes_date = getDaysSinceSave(like.likes_date);
                    $http.get(`/rest/find-albums?id=` + like.post_id.post_id).then(function(respAlbums) {
                        if (respAlbums.data && respAlbums.data.length > 0) {
                            console.log(respAlbums.data);
                            console.log(respAlbums.data[0].albums_name);
                            like.firstImageLike = respAlbums.data[0].albums_name;
                        }

                    });
                });

            }
        });
    };

    $rootScope.listPostLike();

    $http.get(`/rest/user`).then(resp => {
        if (resp.data) {
            $rootScope.$u = resp.data;
            console.log(resp.data);

            $http.get(`/rest/list-post-expirect?id=` + $rootScope.$u.username).then(resp => {
                if (resp.data) {
                    $rootScope.sumNotification = resp.data.length;
                    console.log(resp.data);
                }
            });
        }
    });


    $http.get(`/rest/pay`).then(resp => {
        if (resp.data) {
            $rootScope.$pay = resp.data;
            console.log($rootScope.$pay);
        }
    });

    $http.get(`/type-property`).then(resp => {
        if (resp.data) {
            $rootScope.types = resp.data;
            console.log($rootScope.types);
        }
    });

    $http.get(`/rest/province`).then(resp => {
        if (resp.data) {
            $rootScope.provinces = resp.data;
            console.log($rootScope.provinces);
        }
    });

    $http.get(`/rest/service-pack`).then(resp => {
        if (resp.data) {
            $rootScope.pack = resp.data;

            console.log($rootScope.pack[0].services_id === 1);

        }
    });
    $rootScope.checked = function(id) {
        return $rootScope.pack[0].services_id === id;
    }

    /* List Post */
    $http.get(`/rest/list-post`).then(function(respPostAll) {
        if (respPostAll.data) {
            $rootScope.listPost = respPostAll.data;
        }
    });
    /* List Post */

    /* List Post (diamond) */
    $http.get(`/rest/list-post-diamond`).then(function(respPostAll) {
        if (respPostAll.data) {
            $rootScope.listPostDiamond = respPostAll.data;
            $rootScope.listPostDiamond.forEach(function(post) {
                $http.get(`/rest/find-albums?id=` + post.post_id).then(function(respAlbums) {
                    if (respAlbums.data && respAlbums.data.length > 0) {
                        console.log(respAlbums.data);
                        console.log(respAlbums.data[0].albums_name);
                        post.firstImage = respAlbums.data[0].albums_name;
                    }

                    var priceString = post.price.toString();
                    if (post.price && priceString.length === 7) {
                        var millions = priceString.slice(0, 1);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 8) {
                        var millions = priceString.slice(0, 2);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 9) {
                        var millions = priceString.slice(0, 3);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 10) {
                        var millions = priceString.slice(0, 1);
                        post.price = millions + ' tỷ';
                    }
                    if (post.price && priceString.length === 11) {
                        var millions = priceString.slice(0, 2);
                        post.price = millions + ' tỷ';
                    }
                    if (post.price && priceString.length === 12) {
                        var millions = priceString.slice(0, 3);
                        post.price = millions + ' tỷ';
                    } else {
                        post.price = post.price;
                    }
                });


            });
        }
    });
    /* List Post (diamond) */

    /* List Post (Hots New) */
    $http.get(`/rest/list-post-hots-new`).then(function(respPostAll) {
        if (respPostAll.data) {
            $rootScope.listPostHotsNew = respPostAll.data;
            $rootScope.listPostHotsNew.forEach(function(post) {
                $http.get(`/rest/find-albums?id=` + post.post_id).then(function(respAlbums) {
                    if (respAlbums.data && respAlbums.data.length > 0) {
                        console.log(respAlbums.data);
                        console.log(respAlbums.data[0].albums_name);
                        post.firstImage = respAlbums.data[0].albums_name;
                    }

                    var priceString = post.price.toString();
                    if (post.price && priceString.length === 7) {
                        var millions = priceString.slice(0, 1);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 8) {
                        var millions = priceString.slice(0, 2);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 9) {
                        var millions = priceString.slice(0, 3);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 10) {
                        var millions = priceString.slice(0, 1);
                        post.price = millions + ' tỷ';
                    }
                    if (post.price && priceString.length === 11) {
                        var millions = priceString.slice(0, 2);
                        post.price = millions + ' tỷ';
                    }
                    if (post.price && priceString.length === 12) {
                        var millions = priceString.slice(0, 3);
                        post.price = millions + ' tỷ';
                    } else {
                        post.price = post.price;
                    }
                });
            });
        }
    });
    /* List Post (Hots New) */

    /* List Post (Often) */
    $http.get(`/rest/list-post-often`).then(function(respPostAll) {
        if (respPostAll.data) {
            $rootScope.listPostOften = respPostAll.data;
            $rootScope.listPostOften.forEach(function(post) {
                $http.get(`/rest/find-albums?id=` + post.post_id).then(function(respAlbums) {
                    if (respAlbums.data && respAlbums.data.length > 0) {
                        console.log(respAlbums.data);
                        console.log(respAlbums.data[0].albums_name);
                        post.firstImage = respAlbums.data[0].albums_name;
                    }

                    var priceString = post.price.toString();
                    if (post.price && priceString.length === 7) {
                        var millions = priceString.slice(0, 1);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 8) {
                        var millions = priceString.slice(0, 2);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 9) {
                        var millions = priceString.slice(0, 3);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 10) {
                        var millions = priceString.slice(0, 1);
                        post.price = millions + ' tỷ';
                    }
                    if (post.price && priceString.length === 11) {
                        var millions = priceString.slice(0, 2);
                        post.price = millions + ' tỷ';
                    }
                    if (post.price && priceString.length === 12) {
                        var millions = priceString.slice(0, 3);
                        post.price = millions + ' tỷ';
                    } else {
                        post.price = post.price;
                    }
                });
            });
        }
    });
    /* List Post (Often) */

    /* Introducing The Post */
    $http.get(`/rest/introducing-the-post`).then(function(respPost) {
        if (respPost.data) {
            $rootScope.introThePost = respPost.data;
            console.log($rootScope.introThePost);
            $http.get(`/rest/find-albums?id=` + $rootScope.introThePost.post_id).then(function(respAlbums) {
                if (respAlbums.data) {
                    $rootScope.albumsPost = respAlbums.data;
                    console.log($rootScope.albumsPost[0].albums_name);
                }

            });
        }
    });
    /* Introducing The Post */

    /* Type Property Suggest */
    $http.get(`/type-property-suggest`).then(function(response) {
        if (response.data) {
            $rootScope.suggest = response.data;
        }
    });
    /* Type Property Suggest */

    /* Post For You */
    $http.get(`/rest/post-for-you`).then(function(response) {
        if (response.data) {
            $rootScope.postForYou = response.data;
            $rootScope.postForYou.forEach(function(post) {
                $http.get(`/rest/find-albums?id=` + post.post_id).then(function(respAlbums) {
                    if (respAlbums.data && respAlbums.data.length > 0) {
                        console.log(respAlbums.data);
                        console.log(respAlbums.data[0].albums_name);
                        post.firstImage = respAlbums.data[0].albums_name;
                    }
                    console.log(typeof post.price);
                    var priceString = post.price.toString();
                    if (post.price && priceString.length === 7) {
                        var millions = priceString.slice(0, 1);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 8) {
                        var millions = priceString.slice(0, 2);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 9) {
                        var millions = priceString.slice(0, 3);
                        post.price = millions + ' triệu';
                    }
                    if (post.price && priceString.length === 10) {
                        var millions = priceString.slice(0, 1);
                        post.price = millions + ' tỷ';
                    }
                    if (post.price && priceString.length === 11) {
                        var millions = priceString.slice(0, 2);
                        post.price = millions + ' tỷ';
                    }
                    if (post.price && priceString.length === 12) {
                        var millions = priceString.slice(0, 3);
                        post.price = millions + ' tỷ';
                    } else {
                        post.price = post.price;
                    }
                });


            });
        }
    });
    /* Post For You */

});

app.controller("mycontroller", function($scope, $http, $rootScope, $window) {

    const url = `http://localhost:8080/rest/files/img`;
    const urlAvt = `http://localhost:8080/rest/files/avatar`;
    $scope.sizeError = false;
    $scope.duplicateError = false;
    $scope.containsHumanError = false;
    $scope.filenames = [];

    // Mảng lưu trữ các giá trị hash của các tệp ảnh đã tải lên
    var uploadedFileHashes = [];

    $scope.selectedProvince = '';
    $scope.selectedDistrict = '';
    $scope.selectedWard = '';
    $scope.street = '';
    $scope.key = '';
    $scope.province = 'null';
    $scope.typePost = 0;
    $scope.marker = 0;

    // post page
    $scope.currentPage = 0;
    $scope.totalPages = 0;
    $scope.pages = [];

    //$scope.inputNumber = 0;
    $scope.currentDate = new Date();

    var searchParams = new URLSearchParams($window.location.search);
    var postIdFromQueryString = searchParams.get('id');

    $scope.copyIconClass = 'fa-regular fa-copy';

    // copy address 
    $scope.copyAddress = function(address) {
        navigator.clipboard.writeText(address);

        $scope.copyIconClass = 'fa-solid fa-check';
        document.getElementById('copyIcon').style.color = '#4285f4';

        setTimeout(function() {
            $scope.$apply(function() {
                $scope.copyIconClass = 'fa-regular fa-copy';
                document.getElementById('copyIcon').style.color = '#707070';
            });
        }, 1500);
    };

    $scope.postsPage = function(page) {
        $http.get(`/rest/list-post-page?page=` + page).then(function(response) {
            if (response.data) {
                $scope.postPage = response.data.content;
                $scope.currentPage = response.data.number;
                $scope.totalPages = response.data.totalPages;
                $scope.pages = new Array(response.data.totalPages);

                $scope.currentPage = $scope.currentPage + 1;
                console.log($scope.postPage);
                $scope.postPage.forEach(function(post) {
                    $http.get(`/rest/find-albums?id=` + post.post_id).then(function(respAlbums) {
                        if (respAlbums.data && respAlbums.data.length > 0) {
                            console.log(respAlbums.data);
                            console.log(respAlbums.data[0].albums_name);
                            post.firstImage = respAlbums.data[0].albums_name;
                        }
                        console.log(typeof post.price);
                        var priceString = post.price.toString();
                        if (post.price && priceString.length === 7) {
                            var millions = priceString.slice(0, 1);
                            post.price = millions + ' triệu';
                        }
                        if (post.price && priceString.length === 8) {
                            var millions = priceString.slice(0, 2);
                            post.price = millions + ' triệu';
                        }
                        if (post.price && priceString.length === 9) {
                            var millions = priceString.slice(0, 3);
                            post.price = millions + ' triệu';
                        }
                        if (post.price && priceString.length === 10) {
                            var millions = priceString.slice(0, 1);
                            post.price = millions + ' tỷ';
                        }
                        if (post.price && priceString.length === 11) {
                            var millions = priceString.slice(0, 2);
                            post.price = millions + ' tỷ';
                        }
                        if (post.price && priceString.length === 12) {
                            var millions = priceString.slice(0, 3);
                            post.price = millions + ' tỷ';
                        } else {
                            post.price = post.price;
                        }
                    });
                });
            }
        });
    }
    $scope.getPages = function(totalPages) {
        var pages = [];
        for (var i = 1; i <= totalPages; i++) {
            pages.push(i);
        }
        return pages;
    };

    $scope.postsPage(1);

    $http.get('/post-id/' + postIdFromQueryString).then(function(response) {
        $scope.post = response.data;
        console.log($scope.post);
        $http.get('/map/' + $scope.post.post_id).then(function(response) {
            console.log(response.data);
            if (response.data) {
                $scope.maps = response.data;
                var coordinates = $scope.maps.maps_address.split(',');

                var lng = parseFloat(coordinates[0]);
                var lat = parseFloat(coordinates[1]);

                goongjs.accessToken = 'GsUEtexN59WDYJ8cfpBllo4zFhQU17QbU1yGYNx2';

                var map = new goongjs.Map({
                    container: 'mapDetail',
                    style: 'https://tiles.goong.io/assets/goong_map_web.json',
                    center: [lng, lat],
                    zoom: 13
                });
                var ll = new goongjs.LngLat(lng, lat);
                marker = new goongjs.Marker()
                    .setLngLat(ll)
                    .addTo(map);
            }
        });

        $http.get('/rest/find-albums?id=' + $scope.post.post_id).then(function(response) {
            if (response.data) {
                $scope.filenamesUpdate = [];
                for (let i = 0; i < response.data.length; i++) {
                    $scope.filenamesUpdate.push(response.data[i].albums_name);
                }
                console.log($scope.filenamesUpdate);
            }
        });
    });

    // Upload file
    $scope.url = function(filename) {
        return `${url}/${filename}`;
    }

    $scope.urlAvt = function(filename) {
        var type = null;
        if (filename != '' && filename != type) {
            return `${urlAvt}/${filename}`;
        } else {
            return `${urlAvt}/profile.png`;
        }

    }

    // $scope.updateKey = function(key) {
    //     console.log(key == '');
    //     if (key == '') {
    //         key = 'null';
    //         $scope.key = key;
    //     }
    // };

    $scope.searchPosts = function() {
        if ($scope.key == '') {
            $http.get(`/rest/search-post?title=` + 'null' + `&address=` + 'null' + `&province=` + $scope.province + `&type=` + $scope.typePost)
                .then(function(response) {
                    $scope.postPage = response.data;
                    $scope.postPage.forEach(function(post) {
                        $http.get(`/rest/find-albums?id=` + post.post_id).then(function(respAlbums) {
                            if (respAlbums.data && respAlbums.data.length > 0) {
                                console.log(respAlbums.data);
                                console.log(respAlbums.data[0].albums_name);
                                post.firstImage = respAlbums.data[0].albums_name;
                            }
                            console.log(typeof post.price);
                            var priceString = post.price.toString();
                            if (post.price && priceString.length === 7) {
                                var millions = priceString.slice(0, 1);
                                post.price = millions + ' triệu';
                            }
                            if (post.price && priceString.length === 8) {
                                var millions = priceString.slice(0, 2);
                                post.price = millions + ' triệu';
                            }
                            if (post.price && priceString.length === 9) {
                                var millions = priceString.slice(0, 3);
                                post.price = millions + ' triệu';
                            }
                            if (post.price && priceString.length === 10) {
                                var millions = priceString.slice(0, 1);
                                post.price = millions + ' tỷ';
                            }
                            if (post.price && priceString.length === 11) {
                                var millions = priceString.slice(0, 2);
                                post.price = millions + ' tỷ';
                            }
                            if (post.price && priceString.length === 12) {
                                var millions = priceString.slice(0, 3);
                                post.price = millions + ' tỷ';
                            } else {
                                post.price = post.price;
                            }
                        });


                    });
                });
        } else {
            $http.get(`/rest/search-post?title=` + $scope.key + `&address=` + $scope.key + `&province=` + $scope.province + `&type=` + $scope.typePost)
                .then(function(response) {
                    $scope.postPage = response.data;
                    $scope.postPage.forEach(function(post) {
                        $http.get(`/rest/find-albums?id=` + post.post_id).then(function(respAlbums) {
                            if (respAlbums.data && respAlbums.data.length > 0) {
                                console.log(respAlbums.data);
                                console.log(respAlbums.data[0].albums_name);
                                post.firstImage = respAlbums.data[0].albums_name;
                            }
                            console.log(typeof post.price);
                            var priceString = post.price.toString();
                            if (post.price && priceString.length === 7) {
                                var millions = priceString.slice(0, 1);
                                post.price = millions + ' triệu';
                            }
                            if (post.price && priceString.length === 8) {
                                var millions = priceString.slice(0, 2);
                                post.price = millions + ' triệu';
                            }
                            if (post.price && priceString.length === 9) {
                                var millions = priceString.slice(0, 3);
                                post.price = millions + ' triệu';
                            }
                            if (post.price && priceString.length === 10) {
                                var millions = priceString.slice(0, 1);
                                post.price = millions + ' tỷ';
                            }
                            if (post.price && priceString.length === 11) {
                                var millions = priceString.slice(0, 2);
                                post.price = millions + ' tỷ';
                            }
                            if (post.price && priceString.length === 12) {
                                var millions = priceString.slice(0, 3);
                                post.price = millions + ' tỷ';
                            } else {
                                post.price = post.price;
                            }
                        });


                    });
                });
        }

    }

    $scope.list = function() {
        $http.get(url).then(response => {
            $scope.filenames = response.data;
        }).catch(err => {
            console.error(err)
        })
    }

    function calculateFileHash(file, callback) {
        var reader = new FileReader();
        reader.onload = function() {
            var arrayBuffer = this.result;
            crypto.subtle.digest("SHA-256", arrayBuffer).then(function(hashBuffer) {
                var hashArray = Array.from(new Uint8Array(hashBuffer));
                var hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
                callback(hashHex);
            });
        };
        reader.readAsArrayBuffer(file);
    }

    $scope.upload = function(files) {

        const form = new FormData();
        $scope.sizeError = false;
        $scope.dimensionError = false;

        // Mảng lưu trữ các giá trị hash của các tệp ảnh mới tải lên
        var newFileHashes = [];

        for (var i = 0; i < files.length; i++) {
            var file = files[i];

            console.log("index - 3")
                // Index 3
            calculateFileHash(file, function(hash) {
                console.log(hash + " ,index = 3.1");
                var img = new Image();
                img.src = URL.createObjectURL(file);
                img.onload = function() {
                    containsHuman(file, function(containsHuman) {
                        if (containsHuman) {
                            $scope.containsHumanError = true;
                            $scope.$apply();
                            console.log("Ảnh Mờ")
                            return;
                        }
                        
                        console.log("Kiểm tra img co bị trùng lặp hay không" + " ,index = 3.4");
                        if (uploadedFileHashes.includes(hash)) {
                            $scope.duplicateError = true;
                            $scope.$apply();
                            console.log("Trùng lặp hình ảnh tải lên");
                            return;
                        } else {
                            console.log("ELSE HASH")
                            uploadedFileHashes.push(hash);
                        }

                        addFiles(file, form);
                    })
                };

            });
        }
    }

    function addFiles(file, form) {
        console.log("Đẩy lên form hình ảnh")
        form.append("files", file);

        $http.post(url, form, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(response => {
            $scope.filenames.push(...response.data)
        }).catch(err => {
            console.error(err)
        })
    }

    // Upload Avatar
    $scope.uploadAvatar = function(files) {
        const form = new FormData();
        for (var i = 0; i < files.length; i++) {
            var file = files[i];
            form.append("files", file);

            $http.post(`http://localhost:8080/rest/files/avatar`, form, {
                transformRequest: angular.identity,
                headers: { 'Content-Type': undefined }
            }).then(response => {
                console.log(response.data);
                var image = response.data[0].split('.')[0] + '.' + response.data[0].split('.')[1];

                console.log(image);
                $http.put(`/rest/update-avatar-user?avt=` + image).then(function(response) {
                    console.log(response.data.avatar);
                    $rootScope.$u.avatar = response.data.avatar;
                })
            }).catch(err => {
                console.error(err)
            })
        }


    }

    $scope.delete = function(filename) {
        $http.delete(`${url}/${filename}`).then(response => {
            let i = $scope.filenames.findIndex(name => name === filename);
            $scope.filenames.splice(i, 1);
            uploadedFileHashes.splice(i, 1);
            console.log(uploadedFileHashes.splice(i, 1) + "delete array hash")
        }).catch(err => {
            console.error(err)
        })
    }


    function containsHuman(file, callback) {
        var apiKey = 'AIzaSyCcG1NcZu65SFi9uxG3MDekuI_SWV6dMwg';
        var apiUrl = 'https://vision.googleapis.com/v1/images:annotate?key=' + apiKey;

        var request = {
            requests: [{
                image: {
                    content: base64Encode(file)
                },
                features: [{
                    type: 'LABEL_DETECTION'
                }]
            }]
        };

        $http.post(apiUrl, request).then(response => {
            var labels = response.data.responses[0].labelAnnotations;
            var containsHuman = labels.some(label => label.description.toLowerCase() === 'human');
            callback(containsHuman);
        }).catch(err => {
            console.error(err);
            callback(false);
        });
    }

    // Hàm mã hóa file thành base64
    function base64Encode(file) {
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function() {
            var base64 = reader.result.split(',')[1];
            callback(base64);
        };
    }

    // $scope.list();

    $scope.loadDistricts = function() {
        $http.get(`/rest/district?id=` + $scope.selectedProvince)
            .then(resp => {
                if (resp.data) {
                    $rootScope.districts = resp.data;
                    console.log($rootScope.districts);
                }
            });
    };

    $scope.loadWards = function() {
        $http.get(`/rest/wards?id=` + $scope.selectedDistrict)
            .then(function(response) {
                $scope.wards = response.data;
            });
    };

    $scope.updateAddress = function() {
        var provinceName = $scope.provinces.find(function(province) {
            var number = parseInt($scope.selectedProvince, 10);

            var provinceIdType = typeof province.province_id;
            var selectedProvinceType = typeof $scope.selectedProvince;

            // console.log("Kiểu dữ liệu của province.province_id: " + provinceIdType);
            // console.log("Kiểu dữ liệu của $scope.selectedProvince: " + selectedProvinceType);

            // console.log(province.province_id + ' đầu')
            // console.log(number + ' đầu1')

            // console.log(province.province_id === number)
            if (province.province_id === number) {
                return province.name;
            }
            return null;
        });
        var districtName = $scope.districts.find(function(district) {
            var number = parseInt($scope.selectedDistrict, 10);
            if (district.district_id === number) {
                return district.name;
            }
            return null;
        });
        var wardName = $scope.wards.find(function(ward) {
            var number = parseInt($scope.selectedWard, 10);
            if (ward.wards_id === number) {
                return ward.name;
            }
            return null;
        });
        if ($scope.street.length == 0) {
            $scope.addresss = wardName.name + ', ' + districtName.name + ', ' + provinceName.name;
            $scope.post.addresss = wardName.name + ', ' + districtName.name + ', ' + provinceName.name;
        } else {
            $scope.addresss = $scope.street + ', ' + wardName.name + ', ' + districtName.name + ', ' + provinceName.name;
            $scope.post.addresss = $scope.street + ', ' + wardName.name + ', ' + districtName.name + ', ' + provinceName.name;
        }
    };

    $http.get(`/rest/province`).then(function(response) {
        $scope.provinces = response.data;
    });

    $scope.checkLikesAction = function() {
        var username = $rootScope.$u.username;
        $http.get(`/find-by-post-likes/` + username + '/' + postIdFromQueryString).then(response => {
            if (response.data) {
                $rootScope.likesAction = response.data.likes_status;
            }
        });
    }

    $scope.toggleLike = function() {
        $scope.post_id = postIdFromQueryString;
        console.log($scope.post_id);
        $http.get(`/find-by-post-likes`)
            .then(function(response) {
                console.log(response.data);
                $rootScope.likes = response.data;
                var found = false;
                $rootScope.likes.forEach(like => {
                    if (like.post_id.post_id === $scope.post_id) {
                        found = true;
                        $http.delete(`/likes-delete/` + $scope.post_id)
                            .then(function() {

                            })
                            .catch(function(error) {
                                console.error("Lỗi xóa bài viết khỏi danh sách yêu thích: ", error);
                            });
                    }
                });
                // search post id
                $http.get(`/post-id/` + $scope.post_id).then(function(response) {
                    $scope.post = response.data;
                    console.log($scope.post);
                });

                $http.get(`/rest/user`).then(resp => {
                    if (resp.data) {
                        $rootScope.$u = resp.data;
                        console.log($rootScope.$u);
                    }
                });
                if (!found) {
                    $scope.likes = {
                        likes_status: true,
                        likes_date: new Date(),
                        post_id: $scope.post,
                        users: $rootScope.$u
                    };
                    var like = $scope.likes;
                    $http.post('/likes-add', like)
                        .then(function() {

                        })
                        .catch(function(error) {
                            console.error('Lỗi thêm bài viết vào danh sách yêu thích: ', error);
                        });
                }
            })
            .catch(function(error) {
                console.error("Lỗi truy vấn danh sách yêu thích:", error);
            });
    };

    $scope.managerToggleLike = function(post_id) {
        $http.get(`/find-by-post-likes`)
            .then(function(response) {
                console.log(response.data);
                $rootScope.likes = response.data;
                var found = false;
                $rootScope.likes.forEach(like => {
                    if (like.post_id.post_id === post_id) {
                        found = true;
                        $http.delete(`/likes-delete/` + post_id)
                            .then(function() {
                                $rootScope.listPostLike();
                            })
                            .catch(function(error) {
                                console.error("Lỗi xóa bài viết khỏi danh sách yêu thích: ", error);
                            });
                    }
                });
                // search post id
                $http.get(`/post-id/` + post_id).then(function(response) {
                    $scope.post = response.data;
                    console.log($scope.post);
                });

                $http.get(`/rest/user`).then(resp => {
                    if (resp.data) {
                        $rootScope.$u = resp.data;
                        console.log($rootScope.$u);
                    }
                });
                if (!found) {
                    $scope.likes = {
                        likes_status: true,
                        likes_date: new Date(),
                        post_id: $scope.post,
                        users: $rootScope.$u
                    };
                    var like = $scope.likes;
                    $http.post('/likes-add', like)
                        .then(function() {

                        })
                        .catch(function(error) {
                            console.error('Lỗi thêm bài viết vào danh sách yêu thích: ', error);
                        });
                }
            })
            .catch(function(error) {
                console.error("Lỗi truy vấn danh sách yêu thích:", error);
            });
    };

    $scope.bed = 1;
	$scope.totalDay=0;
    $scope.increaseBedroomCount = function() {
        console.log($scope.post.bed);
        if ($scope.bed < 100) {
            $scope.bed++;
            $scope.post.bed++;
        }
    };

    $scope.decreaseBedroomCount = function() {
        if ($scope.bed > 1 || $scope.post.bed > 1) {
            $scope.bed--;
            $scope.post.bed--;
        } else {
            $scope.post.bed = 1;
        }
    };

    $scope.toilet = 1;

    $scope.increaseToiletCount = function() {
        if ($scope.toilet < 100) {
            $scope.toilet++;
            $scope.post.toilet++;
        }
    };

    $scope.decreaseToiletCount = function() {
        if ($scope.toilet > 1 || $scope.post.toilet > 1) {
            $scope.toilet--;
            $scope.post.toilet--;
        } else {
            $scope.post.toilet = 1;
        }
    };

    $scope.endDate = function(nthday) {
		$scope.totalDay = nthday;
		console.log("hehe: "+$scope.totalDay);
        //var testType = typeof $scope.inputNumber;
        console.log("End Date kiểu dữ liệu: " + typeof nthday);
        var nday = parseInt(nthday, 10);
        console.log("End Date kiểu dữ liệu 2: " + nday);
        if (!isNaN(nday) && nday >= 0) {
            var currentDate = new Date($scope.currentDate);
            currentDate.setDate(currentDate.getDate() + nday);
            console.log(currentDate);
            return $scope.currentEndDate = currentDate;
        } else {
            return $scope.currentEndDate = "Số ngày không hợp lệ.";
        }
    }

    $scope.getService = function(id) {
        $http.get(`/rest/service-pack-findById?id=` + id).then(resp => {
            console.log(resp.data);
            if (resp.data) {
                console.log(id);
                $scope.service = resp.data;
            }
        });
    }

    $scope.searchTypeCreate = function() {
        $http.get(`/type-property-findById?id=` + $scope.types_id).then(resp => {
            if (resp.data) {
                $rootScope.type = resp.data;
                console.log($rootScope.type);
            }
        });
    }
    $scope.chooseMarker = function() {
            $scope.marker = 1;
        }
        // Create Post
    $scope.createPost = function() {
        $http.get(`/rest/user`).then(resp => {
            if (resp.data) {
                $rootScope.$u = resp.data;
                console.log(resp.data);
            }
        });

        var post = {
            post_title: $scope.post_title,
            post_content: $scope.post_content,
            create_at: new Date(),
            end_date: $scope.currentEndDate,
            acreage: $scope.acreage,
            price: $scope.price,
            addresss: $scope.addresss,
            linkVideo: $scope.linkVideo,
            services_id: $scope.service,
            types_id: $scope.type,
            direction: $scope.direction,
            bed: $scope.bed,
            juridical: $scope.juridical,
            balcony: $scope.balcony,
            toilet: $scope.toilet,
            interior: $scope.interior,
            active: true,
            users_id: $rootScope.$u,
            deletedAt: false
        };

        var transaction = {
            create_at: new Date(),
            users: $rootScope.$u
        };
        $http.post(`/create-post`, post).then(function(responsePost) {
            var lng = marker.getLngLat();
            var toString = '' + lng.lng + ',' + lng.lat;
            var maps = {
                maps_address: toString,
                post_id: responsePost.data
            };
            $http.post(`/create-mapAddress`, maps).then(function(response) {
                console.log("mapAdress: " + response.data);
            });

            $http.put(`/rest/set-money-pay?user=` + $rootScope.$u.username + `&money=` + ($scope.service.services_price * 1000)*$scope.totalDay).then(function(response) {

                },
                function(error) {
                    swal("Lỗi!", "Lỗi ví tiền của bạn!", "error");
                    $http.put(`/post-id/false/` + responsePost.data.post_id).then(function(response) {

                    }, function(error) {

                    });
                });

            $http.post(`/rest/create-transaction`, transaction).then(function(response) {
                const today = new Date();
                var detailTransaction = {
                    price: ($scope.service.services_price * 1000)*$scope.totalDay,
                    transactions_type: false,
                    timer: today.toLocaleTimeString("en-US"),
                    account_get: $rootScope.$pay.pay_id,
                    fullname_get: $rootScope.$u.fullname,
                    bank_code: null,
                    transactions_id: response.data
                };
                $http.post(`/rest/create-detail-transaction`, detailTransaction).then(function(response) {}, function(err) {})

            }, function(err) {});

            for (let i = 0; i < $scope.filenames.length; i++) {
                console.log($scope.filenames[i].split('.')[0] + '.' + $scope.filenames[i].split('.')[1]);
                var image = $scope.filenames[i].split('.')[0] + '.' + $scope.filenames[i].split('.')[1];

                var album = {
                    albums_name: image,
                    post_id: responsePost.data
                }

                $http.post(`/rest/create-albums`, album).then(function(response) {

                }, function(error) {
                    swal("Lỗi!", "Thêm Ảnh Thất Bại!", "error");
                })
            }
            swal("Thành Công!", "Đăng bài thành công!", "success");
            window.location.href = "/home/manager/post";
        }, function(error) {
            swal("Lỗi!", "Đăng bài thất bại!", "error");

        });


    }

    $scope.deleteUpdate = function(filename) {
        $http.get(`/rest/find-by?name=` + filename + `&id=` + $scope.post.post_id).then(function(response) {
            console.log(response.data);
            $scope.albums_id = response.data.albums_id;
            $http.delete(`/rest/delete-albums?id=` + $scope.albums_id).then(function(response) {
                $http.delete(`${url}/${filename}`).then(response => {
                    let i = $scope.filenamesUpdate.findIndex(name => name === filename);
                    $scope.filenamesUpdate.splice(i, 1);
                    uploadedFileHashes.splice(i, 1);
                    console.log(uploadedFileHashes.splice(i, 1) + "delete array hash")
                }).catch(err => {
                    console.error(err)
                })
            })
        })
    }

    function updateFiles(file, form) {
        console.log("Đẩy lên form hình ảnh")
        form.append("files", file);

        $http.post(url, form, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(response => {
            console.log(response.data);
            [$scope.filenamesUpdate].push(...response.data)
        }).catch(err => {
            console.error(err)
        })
    }

    function loadImgUpdates() {
        $http.get('/post-id/' + postIdFromQueryString).then(function(response) {
            $scope.post = response.data;
            console.log($scope.post);
            $http.get('/rest/find-albums?id=' + $scope.post.post_id).then(function(response) {
                if (response.data) {
                    $scope.filenamesUpdate = [];
                    for (let i = 0; i < response.data.length; i++) {
                        $scope.filenamesUpdate.push(response.data[i].albums_name);
                    }
                    console.log($scope.filenamesUpdate);
                }
            });
        });
    }

    $scope.uploadUpdate = function(files) {

        const form = new FormData();
        $scope.sizeError = false;
        $scope.dimensionError = false;

        // Mảng lưu trữ các giá trị hash của các tệp ảnh mới tải lên
        var newFileHashes = [];

        for (var i = 0; i < files.length; i++) {
            var file = files[i];
            var fileName = files[i].name;
            console.log(fileName);
            
            form.append("files", file);
            $http.post(url, form, {
                transformRequest: angular.identity,
                headers: { 'Content-Type': undefined }
            }).then(response => {
                console.log(response.data);
                //[$scope.filenamesUpdate].push(...response.data)
                [$scope.filenamesUpdate] = response.data;
                for (let i = 0; i < [$scope.filenamesUpdate].length; i++) {
                    console.log([$scope.filenamesUpdate])
                    console.log([$scope.filenamesUpdate][i].split('.')[0] + '.' + [$scope.filenamesUpdate][i].split('.')[1]);
                    var image = [$scope.filenamesUpdate][i].split('.')[0] + '.' + [$scope.filenamesUpdate][i].split('.')[1];
                    $http.get(`/rest/find-by?name=` + image + `&id=` + $scope.post.post_id).then(function(response) {
                        if (response.data) {
                            console.log("Cập nhật hình 820");
                            $http.get('/rest/find-albums?id=' + $scope.post.post_id).then(function(response) {
                                if (response.data) {
                                    for (let i = 0; i < response.data.length; i++) {
                                        $scope.albums_id = [response.data[i].albums_id];
                                        console.log($scope.filenamesUpdate);
                                    }
                                }
                            });

                            for (let i = 0; i < $scope.albums_id.length; i++) {
                                $http.get('/post-id/' + $scope.post.post_id).then(function(response) {
                                    $scope.post = response.data;
                                });
                                var album = {
                                    albums_id: $scope.albums_id[i],
                                    albums_name: image,
                                    post_id: $scope.post
                                }

                                $http.put(`/rest/update-albums`, album).then(function(response) {
                                    console.log(response.data);
                                    loadImgUpdates();
                                }, function(error) {
                                    alert('Đã có lỗi xảy ra: ' + error.data.message);
                                    swal("Lỗi!", "Cập nhật ảnh thất bại!", "error");
                                });
                            }
                        } else {
                            console.log("Thêm hình 842");
                            $http.get('/post-id/' + $scope.post.post_id).then(function(response) {
                                $scope.post = response.data;
                            });
                            var album = {
                                albums_name: image,
                                post_id: $scope.post
                            }

                            $http.post(`/rest/create-albums`, album).then(function(response) {
                                if (response.data) {
                                    loadImgUpdates();
                                }

                            }, function(error) {
                                alert('Đã có lỗi xảy ra: ' + error.data.message);
                                swal("Lỗi!", "Thêm Ảnh Thất Bại!", "error");
                            })
                        }
                    });

                }

            }).catch(err => {
                console.error(err)
            })

        }


    }
    $scope.searchType = function() {
        $http.get(`/type-property-findById?id=` + $scope.post.types_id.types_id).then(resp => {
            if (resp.data) {
                $rootScope.typeUpdate = resp.data;
                console.log($rootScope.typeUpdate);
            }
        });
    }

    $scope.post = {
        post_title: "",
        post_content: "",
        create_at: "",
        end_date: "",
        acreage: "",
        price: "",
        addresss: "",
        linkVideo: "",
        services_id: "",
        types_id: "",
        direction: "",
        bed: "",
        juridical: "",
        balcony: "",
        toilet: "",
        interior: "",
        active: true,
        users_id: $rootScope.$u,
        deletedAt: false
    };
    $scope.updatePost = function() {
        $http.put('/update-post', $scope.post).then(function(response) {

            var lng = marker.getLngLat();
            var toString = '' + lng.lng + ',' + lng.lat;
            var maps = {
                maps_address: toString,
                post_id: response.data
            };
            $http.post(`/create-mapAddress`, maps).then(function(response) {
                console.log("mapAdress: " + response.data);
            });
            swal("Thành Công!", "Bài viết đã chỉnh sửa!", "success");
            window.location.href = "/home/manager/post";
        }, function(error) {
            swal("Lỗi!", "Lỗi chỉnh sửa!", "error");
            console.error('Lỗi cập nhật bài viết', error);
        });
    };

    $scope.post = {
        post_title: "",
        post_content: "",
        create_at: new Date(),
        end_date: $scope.currentEndDate,
        acreage: "",
        price: "",
        addresss: "",
        linkVideo: "",
        services_id: $scope.service,
        types_id: $rootScope.typeUpdate,
        direction: "",
        bed: "",
        juridical: "",
        balcony: "",
        toilet: "",
        interior: "",
        active: true,
        users_id: $rootScope.$u,
        deletedAt: false
    };

    $scope.updatePostExpired = function() {
        $scope.post.active = true;
        $http.put('/update-post', $scope.post).then(function(response) {

            var transaction = {
                create_at: new Date(),
                users: $rootScope.$u
            };
            var ll = marker.getLngLat();
            var toString = '' + ll.lng + ',' + ll.lat;
            var maps = {
                maps_address: toString,
                post_id: response.data
            };
            $http.post(`/create-mapAddress`, maps).then(function(response) {
                console.log("mapAdress: " + response.data);
            });

            $http.put(`/rest/set-money-pay?user=` + $rootScope.$u.username + `&money=` + ($scope.service.services_price * 1000)*$scope.totalDay).then(function(response) {

                },
                function(error) {
                    swal("Lỗi!", "Lỗi ví tiền của bạn!", "error");
                    $http.put(`/post-id/false/` + response.data.post_id).then(function(response) {

                    }, function(error) {

                    });
                });

            $http.post(`/rest/create-transaction`, transaction).then(function(response) {
                const today = new Date();
                var detailTransaction = {
                    price: ($scope.service.services_price * 1000)*$scope.totalDay,
                    transactions_type: false,
                    timer: today.toLocaleTimeString("en-US"),
                    account_get: $rootScope.$pay.pay_id,
                    fullname_get: $rootScope.$u.fullname,
                    bank_code: null,
                    transactions_id: response.data
                };

                $http.post(`/rest/create-detail-transaction`, detailTransaction).then(function(response) {}, function(err) {})
            }, function(err) {
                swal("Good job!", "Lỗi - Giao Dịch!", "success");
            });
            swal("Thành Công!", "Bài viết đã đăng!", "success");
            window.location.href = "/home/manager/post";
        }, function(error) {
            swal("Lỗi!", "Lỗi đăng bài!", "error");
        });


    };

});

goongjs.accessToken = 'GsUEtexN59WDYJ8cfpBllo4zFhQU17QbU1yGYNx2';

let checkC = 0;
var marker;
var map = new goongjs.Map({
    container: 'map',
    style: 'https://tiles.goong.io/assets/goong_map_web.json',
    center: [105.74241504658403, 10.060186701320404],
    zoom: 13
});

// Add the control to the map.
map.addControl(
    new GoongGeocoder({
        accessToken: 'HjqHdYaa2gKzvL9CZO903kwifZjFrj1cGPcTWdus',
        goongjs: goongjs,
        marker: false,
        placeholder: "Nhập địa chỉ vào đây..."
    })
);
map.addControl(
    new goongjs.GeolocateControl({
        positionOptions: {
            enableHighAccuracy: true,
            timeout: 1000
        },
        trackUserLocation: true,
        showUserLocation: true
    })
);

map.on('click', function(e) {
    if (checkC == 1) {
        // danh dau marker khi click
        marker = new goongjs.Marker()
            .setLngLat(e.lngLat)
            .addTo(map);

        //map.setCenter(e.lngLat);
        checkC = 0;
        var lng = marker.getLngLat();
        var toString = '' + lng.lng + ',' + lng.lat;
    } else {
        console.log("khong chon");
    }

});

// map.on('dbclick', function(e) {
//             console.log("aaaa");
// });

function chooseMarker() {
    checkC = 1;
}

function cancelMarker() {
    marker.remove();
    checkC = 0;
}