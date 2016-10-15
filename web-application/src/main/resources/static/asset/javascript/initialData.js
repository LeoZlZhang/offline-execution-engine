/**
 * Created by leo_zlzhang on 10/10/2016.
 * for jstree and jseditor
 */

var editor = new JSONEditor(
    document.getElementById('jsoneditor'),
    {
        mode: 'tree',
        modes: ['code', 'form', 'text', 'tree', 'view'], // allowed modes
        onError: function (err) {
            alert(err.toString());
        },
        onModeChange: function (newMode, oldMode) {
            console.log('Mode switched from', oldMode, 'to', newMode);
        }
    },
    null);


var lastSelectedTestData = null;
var myCallBack = null;

$('#tree')
    .on('delete_node.jstree ' +
        'rename_node.jstree ' +
        'move_node.jstree ' +
        'paste.jstree', function (e, data) {
        var catalogJson = data.instance.get_json('#', {flat: false});
        saveCatalogData(catalogJson);
    })
    .on('select_node.jstree', function (e, data) {
        if (data.node.icon === 'jstree-file') {
            console.log('load test case:' + data.node.id + ' ' + data.node.text);
            lastSelectedTestData = data.node;
            $('#btn_load').click();

        }
    })
    .on('reload.leo.jstree', function (e, data) {
        data.instance.settings.core.data = loadCatalog();
        data.instance.refresh();
    })
    .on('rename_node.jstree', function (e, data) {
        myCallBack = function () {
            $('#btn_save').click();
            myCallBack = null;
        };
        data.instance.deselect_all();
        data.instance.select_node(data.node.id);
    })
    .on('delete_node.jstree', function (e, data) {
        // if (data.node.icon === 'jstree-file') {
            console.log('delete api data ' + data.node.id + ' ' + data.node.text);
            lastSelectedTestData = data.node;
            $('#btn_delete').click();
        // }
    })
    .jstree({
        'core': {
            'data': loadCatalog(),
            "check_callback": true
        },
        "plugins": ["contextmenu"]
    });

// var mydata = [
//     {
//         "text": "ApiTestData",
//         "icon": true,
//         "data": 'disfoisudofiusdo980fsd9f89f'
//     },
//     {
//         "text": "ApiTestData",
//         "icon": true,
//         "children": [
//             {
//                 "_id": "j1_2",
//                 "text": "advertisement",
//                 "icon": true,
//                 "state": {
//                     "disabled": false,
//                     "opened": true,
//                     "selected": false
//                 },
//                 "children": [
//                     {
//                         "_id": "j1_3",
//                         "text": "AdvertisementController",
//                         "icon": true,
//                         "state": {
//                             "disabled": false,
//                             "opened": true,
//                             "selected": false
//                         },
//                         "children": [
//                             {
//                                 "_id": "j1_4",
//                                 "text": "1_clickAdviertisement",
//                                 "icon": "jstree-file",
//                                 "data": 'disfoisudofiusdo980fsd9f89f',
//                                 "state": {
//                                     "disabled": false,
//                                     "opened": false,
//                                     "selected": false
//                                 },
//                                 "children": []
//                             }
//                         ]
//                     }
//                 ]
//             },
//             {
//                 "_id": "j1_5",
//                 "text": "213",
//                 "icon": "jstree-file",
//                 "state": {
//                     "disabled": false,
//                     "opened": false,
//                     "selected": false
//                 },
//                 "children": []
//             }
//         ]
//     }];
// $('#tree').jstree(true).settings.core.data = mydata;
// $('#tree').jstree(true).refresh();