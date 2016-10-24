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

//load gear and profile
var gearId;
var profileId;
$(function () {

    var gears = loadAllGear();
    for (var i in gears) {
        //noinspection JSUnfilteredForInLoop
        $("#gears").append(
            "<li><a class='item' id='" + gears[i].id + "'>" + gears[i].name + "</a></li>"
        );
        //noinspection JSUnfilteredForInLoop
        $('.alertRow').append(
            "<div class='alert alert-info' role='alert' style='display: none;' id='alert_" + gears[i].id + "'>" +
            "<b>[" + gears[i].name + "]</b> gear is selected" +
            "</div>"
        );
    }

    var profiles = loadAllProfile();
    //noinspection JSDuplicatedDeclaration
    for (i in profiles) {
        //noinspection JSUnfilteredForInLoop
        $("#profiles").append(
            "<li><a class='item' id='" + profiles[i].id + "'>" + profiles[i].name + "</a></li>"
        );
        //noinspection JSUnfilteredForInLoop
        $('.alertRow').append(
            "<div class='alert alert-warning' role='alert' style='display: none;' id='alert_" + profiles[i].id + "'>" +
            "<b>[" + profiles[i].name + "]</b> profile is selected" +
            "</div>"
        );

    }


    //noinspection JSJQueryEfficiency
    $('ul#gears').find('li a.item').on('click', function (e) {
        $('.alert#alert_' + e.target.id).slideDown('slow').delay(1700).slideUp('slow');
        gearId = e.target.id;
    });

    $('ul#profiles li a.item').on('click', function (e) {
        $('.alert#alert_' + e.target.id).slideDown('slow').delay(1700).slideUp('slow');
        profileId = e.target.id;
    });
});