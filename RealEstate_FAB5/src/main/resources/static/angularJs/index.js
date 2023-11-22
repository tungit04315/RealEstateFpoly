const app = angular.module("myapp", []);

app.run(function($rootScope, $http) {
    const MAX_TITLE_LENGTH = 50;

    // Hàm xử lý tiêu đề
    function handleTitle(title) {
        if (title.length > MAX_TITLE_LENGTH) {
            return title.substr(0, MAX_TITLE_LENGTH) + "...";
        } else {
            return title;
        }
    }

    // Hàm tính thời gian lưu bài viết
    function getDaysSinceSave(saveTime) {
        const now = new Date();

        const diff = now - new Date(saveTime);
        console.log(diff);
        return Math.floor(diff / (1000 * 60 * 60 * 24));
    }

    $http.get(`/likes`).then(response => {
        if (response.data) {
            $rootScope.likes = response.data;

            $rootScope.likes.forEach(like => {
                like.post_id.post_title = handleTitle(like.post_id.post_title);
                like.likes_date = getDaysSinceSave(like.likes_date);
                console.log(like.likes_date);
                console.log(like.post_id.post_id);
            });
        }


    });

    $http.get(`/rest/user`).then(resp => {
        if (resp.data) {
            $rootScope.$u = resp.data;
            console.log(resp.data);
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

    $http.get(`/rest/list-post`).then(function(respPostAll) {
        if (respPostAll.data) {
            $rootScope.listPost = respPostAll.data;
        }
    });

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

    $http.get(`/rest/new-post`).then(function(respPost) {
        if (respPost.data) {
            $rootScope.PostAddNew = respPost.data;
            console.log($rootScope.PostAddNew);
            $http.get(`/rest/find-albums?id=` + $rootScope.PostAddNew.post_id).then(function(respAlbums) {
                if (respAlbums.data) {
                    $rootScope.albumsPost = respAlbums.data;
                    console.log($rootScope.albumsPost[0].albums_name);
                }

            });
        }
    });

    $http.get(`/type-property-suggest`).then(function(response) {
        if (response.data) {
            $rootScope.suggest = response.data;
        }
    });

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
    })
});

app.controller("mycontroller", function($scope, $http, $rootScope, $location, $window) {

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
    $scope.province = '';

    //$scope.inputNumber = 0;
    $scope.currentDate = new Date();

    // Upload file

    $scope.url = function(filename) {

        return `${url}/${filename}`;
    }

    $scope.urlAvt = function(filename) {
        if (filename != '') {
            return `${urlAvt}/${filename}`;
        } else {
            return `${urlAvt}/profile.png`;
        }

    }

    $scope.searchPosts = function() {
        $http.get(`/rest/search-post?title=` + $scope.key + `&address=` + $scope.key + `&province=` + $scope.province)
            .then(function(response) {
                $scope.listPostDiamond = response.data;
                $rootScope.listPostDiamond.forEach(function(post) {
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
                        console.log("Kiểm tra img lớn hơn 100KB" + " ,index = 3.2");
                        if ((file.size / 1024) > 100) {
                            $scope.sizeError = true;
                            $scope.$apply();
                            console.log($scope.sizeError);
                            console.log(file.size / 1024);
                            console.log((600 * 400 * 24) / 1024)
                            return;
                        }
                        console.log("Kiểm tra img w:600 - h:400" + " ,index = 3.3");
                        if (img.width !== 600 || img.height !== 400) {
                            $scope.dimensionError = true;
                            $scope.$apply();
                            console.log("img w-h");
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

            // Có chút vấn đề cần được điều chỉnh (
            // Chuyển đổi về If để thực thi trước
            // )
            // var img = new Image();
            // img.src = URL.createObjectURL(file);
            // img.onload = function() {
            //     if (img.width !== 600 || img.height !== 400) {
            //         $scope.dimensionError = true;
            //         $scope.$apply();
            //         console.log("img w-h");
            //         return;
            //     }
            //     return;
            // };

            // Kiểm tra xem trong hình có con người hay không
            // containsHuman(file, function(containsHuman) {
            //     if (containsHuman) {
            //         $scope.containsHumanError = true;
            //         $scope.$apply();
            //         return;
            //     } else {
            //         if (uploadedFileHashes.includes(hash)) {
            //             $scope.duplicateError = true;
            //             $scope.$apply();
            //             return;
            //         } else {
            //             uploadedFileHashes.push(hash);
            //         }

            //         addFiles(file, form);
            //     }
            // });

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

        if ($scope.street) {
            $scope.addresss = $scope.street + ', ' + wardName.name + ', ' + districtName.name + ', ' + provinceName.name;
        } else {
            $scope.addresss = wardName.name + ', ' + districtName.name + ', ' + provinceName.name;
        }
    };

    $http.get(`/rest/province`)
        .then(function(response) {
            $scope.provinces = response.data;
        });

    $scope.toggleLike = function(post_id) {
        $http.get(`/find-by-post-likes`)
            .then(function(response) {
                $rootScope.likes = response.data;
                var found = false;
                $rootScope.likes.forEach(like => {
                    if (like.post_id.post_id === post_id) {
                        found = true;
                        $http.delete(`/likes-delete/${post_id}`)
                            .then(function() {

                            })
                            .catch(function(error) {
                                console.error("Lỗi xóa bài viết khỏi danh sách yêu thích: ", error);
                            });
                    }
                });
                // search post id
                $http.get(`/post-id/${post_id}`).then(function(response) {
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

    $scope.increaseBedroomCount = function() {
        if ($scope.bed < 100) {
            $scope.bed++;
        }
    };

    $scope.decreaseBedroomCount = function() {
        if ($scope.bed > 1) {
            $scope.bed--;
        }
    };

    $scope.toilet = 1;

    $scope.increaseToiletCount = function() {
        if ($scope.toilet < 100) {
            $scope.toilet++;
        }
    };

    $scope.decreaseToiletCount = function() {
        if ($scope.toilet > 1) {
            $scope.toilet--;
        }
    };

    $scope.endDate = function(nthday) {
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
            if (resp.data) {
                console.log(id);
                $scope.service = resp.data;
            }
        });
    }

    $scope.searchType = function() {
        $http.get(`/type-property-findById?id=` + $scope.types_id).then(resp => {
            if (resp.data) {
                $rootScope.type = resp.data;
                console.log($rootScope.type);
            }
        });
    }



    // Create Post
    $scope.createPost = function() {
        console.log($scope.service);
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


        $http.put(`/rest/set-money-pay?user=` + $rootScope.$u.username + `&money=` + $scope.service.services_price * 1000).then(function(response) {
            $http.post(`/create-post`, post)
                .then(function(responsePost) {
                    swal("Thành Công!", "Đăng bài thành công!", "success");
                    $http.post(`/rest/create-transaction`, transaction).then(function(response) {
                        const today = new Date();
                        var detailTransaction = {
                            price: $scope.price,
                            transactions_type: false,
                            timer: today.toLocaleTimeString("en-US"),
                            account_get: $rootScope.$pay.pay_id,
                            fullname_get: $rootScope.$u.fullname,
                            bank_code: null,
                            transactions_id: response.data
                        };
                        $http.post(`/rest/create-detail-transaction`, detailTransaction).then(function(response) {
                            //swal("Good job!", "Giao Dịch - Giao Dịch Chi Tiết!", "success");
                        }, function(err) {
                            alert('Đã có lỗi xảy ra: ' + err.data.message);
                        })
                    }, function(err) {
                        alert('Đã có lỗi xảy ra: ' + err.data.message);
                    })

                    for (let i = 0; i < $scope.filenames.length; i++) {
                        console.log($scope.filenames[i].split('.')[0] + '.' + $scope.filenames[i].split('.')[1]);
                        var image = $scope.filenames[i].split('.')[0] + '.' + $scope.filenames[i].split('.')[1];

                        var album = {
                            albums_name: image,
                            post_id: responsePost.data
                        }

                        $http.post(`/rest/create-albums`, album).then(function(response) {

                        }, function(error) {
                            alert('Đã có lỗi xảy ra: ' + error.data.message);
                            swal("Lỗi!", "Thêm Ảnh Thất Bại!", "error");
                        })
                    }
                }, function(error) {
                    alert('Đã có lỗi xảy ra: ' + error.data.message);
                    swal("Lỗi!", "Đăng bài thất bại!", "error");
                });

        }, function(error) {
            alert('Đã có lỗi xảy ra: ' + error.data.message);
            swal("Lỗi!", "Lỗi ví tiền của bạn!", "error");
        });

    }

    var searchParams = new URLSearchParams($window.location.search);
    var postIdFromQueryString = searchParams.get('id');
    console.log(postIdFromQueryString);
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
            // Index 3
            // calculateFileHash(file, function(hash) {
            //     console.log(hash + " ,index = 3.1");
            //     var img = new Image();
            //     img.src = URL.createObjectURL(file);
            //     img.onload = function() {
            //         containsHuman(file, function(containsHuman) {
            //             if (containsHuman) {
            //                 $scope.containsHumanError = true;
            //                 $scope.$apply();
            //                 console.log("Ảnh Mờ")
            //                 return;
            //             }
            //             console.log("Kiểm tra img lớn hơn 100KB" + " ,index = 3.2");
            //             if ((file.size / 1024) > 100) {
            //                 $scope.sizeError = true;
            //                 $scope.$apply();
            //                 console.log($scope.sizeError);
            //                 console.log(file.size / 1024);
            //                 console.log((600 * 400 * 24) / 1024)
            //                 return;
            //             }
            //             console.log("Kiểm tra img w:600 - h:400" + " ,index = 3.3");
            //             if (img.width !== 600 || img.height !== 400) {
            //                 $scope.dimensionError = true;
            //                 $scope.$apply();
            //                 console.log("img w-h");
            //                 return;
            //             }
            //             console.log("Kiểm tra img co bị trùng lặp hay không" + " ,index = 3.4");
            //             // if (uploadedFileHashes.includes(hash)) {
            //             //     $scope.duplicateError = true;
            //             //     $scope.$apply();
            //             //     console.log("Trùng lặp hình ảnh tải lên");
            //             //     return;
            //             // } else {
            //             //     console.log("ELSE HASH")
            //             //     uploadedFileHashes.push(hash);
            //             // }


            //         })
            //     };

            // });
            // updateFiles(file, form);
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

    $scope.updatePost = function() {
        $scope.post.active = true
        console.log($scope.post);
        $http.put(`/rest/set-money-pay?user=` + $rootScope.$u.username + `&money=` + $scope.service.services_price * 1000).then(function(response) {
            $http.put('/update-post', $scope.post).then(function(response) {
                swal("Thành Công!", "Bài viết đã đăng!", "success");
                console.log(response.data);

                $http.post(`/rest/create-transaction`, transaction).then(function(response) {
                    const today = new Date();
                    var detailTransaction = {
                        price: $scope.price,
                        transactions_type: false,
                        timer: today.toLocaleTimeString("en-US"),
                        account_get: $rootScope.$pay.pay_id,
                        fullname_get: $rootScope.$u.fullname,
                        bank_code: null,
                        transactions_id: response.data
                    };
                    swal("Good job!", "Giao Dịch - Giao Dịch Chi Tiết!", "success");
                    $http.post(`/rest/create-detail-transaction`, detailTransaction).then(function(response) {
                        swal("Good job!", "Giao Dịch - Giao Dịch Chi Tiết!", "success");
                    }, function(err) {
                        swal("Good job!", "Lỗi - Giao Dịch Chi Tiết!", "success");
                        alert('Đã có lỗi xảy ra: ' + err.data.message);
                    })
                }, function(err) {
                    swal("Good job!", "Lỗi - Giao Dịch!", "success");
                    alert('Đã có lỗi xảy ra: ' + err.data.message);
                });


            }, function(error) {
                swal("Lỗi!", "Lỗi đăng bài!", "error");
                console.error('Lỗi cập nhật bài viết', error);
            });
        }, function(error) {
            alert('Đã có lỗi xảy ra: ' + error.data.message);
            swal("Lỗi!", "Lỗi ví tiền của bạn!", "error");
        });

    };
});