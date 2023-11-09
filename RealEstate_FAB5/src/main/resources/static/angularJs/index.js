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


});

app.controller("mycontroller", function($scope, $http, $rootScope) {

    const url = `http://localhost:8080/rest/files/img`;
    $scope.sizeError = false;
    $scope.duplicateError = false;
    $scope.containsHumanError = false;

    // Mảng lưu trữ các giá trị hash của các tệp ảnh đã tải lên
    var uploadedFileHashes = [];

    $scope.selectedProvince = '';
    $scope.selectedDistrict = '';
    $scope.selectedWard = '';
    $scope.street = '';
    //$scope.inputNumber = 0;
    $scope.currentDate = new Date();

    // Upload file

    $scope.url = function(filename) {
        $scope.hide = true;
        return `${url}/${filename}`;
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

    $scope.list();

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
                    swal("Good job!", "Đăng bài thành công!", "success");
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
                            swal("Error!", "Thêm Ảnh Thất Bại!", "error");
                        })
                    }
                }, function(error) {
                    alert('Đã có lỗi xảy ra: ' + error.data.message);
                    swal("Error!", "Đăng bài thất bại!", "error");
                });

        }, function(error) {
            alert('Đã có lỗi xảy ra: ' + error.data.message);
            swal("Error!", "Lỗi ví tiền của bạn!", "error");
        });

    }



});