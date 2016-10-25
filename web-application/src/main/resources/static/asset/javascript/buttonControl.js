$('#btn_load').on('click', function () {
    if (lastSelectedTestData) {

        var jsonData = getApiData({id: lastSelectedTestData.id});

        if (jsonData === false) {
            alert('request server fail to load test data!');
        } else {
            delete jsonData.id;
            editor.set(jsonData);
            if (myCallBack && myCallBack instanceof Function)
                myCallBack();
        }

    } else {
        alert('Can not load, please select a test case from catalog!')
    }
});


$('#btn_save').on('click', function () {
    if (lastSelectedTestData) {
        var jsonData = editor.get();

        if (lastSelectedTestData.id.indexOf('_') < 0)
            jsonData.id = lastSelectedTestData.id;

        jsonData.sourceFileName = lastSelectedTestData.text;

        jsonData = saveTestData(jsonData);

        if (jsonData === false) {
            alert('request server fail to save test data!');
        } else {
            var treeinst = $('#tree').jstree(true);
            //noinspection JSUnresolvedFunction
            treeinst.set_id(lastSelectedTestData, jsonData.id);
            var catalog = treeinst.get_json('#', {flat: false});
            saveCatalogData(catalog);
            delete jsonData.id;
            editor.set(jsonData);
            editor.expandAll();
        }

    } else
        alert('Can not save, please select a test case from catalog!')
});


$('#btn_delete').on('click', function () {
    if (lastSelectedTestData) {
        var childnode = lastSelectedTestData.children_d;
        for (var ai in childnode) {
            //noinspection JSUnfilteredForInLoop
            var result = deleteTestData({id: childnode[ai]});

            if (result === false) {
                alert("request server fail to delete test data");
            }
        }
        result = deleteTestData({id: lastSelectedTestData.id});

        if (result === false) {
            alert("request server fail to delete test data");
        }

        editor.set({});
        lastSelectedTestData = null;
    }
});


$('#btn_execute').on('click', function () {
    if (lastSelectedTestData) {
        if (!gearId) {
            alert("please a gear");
            return;
        }
        if (!profileId) {
            alert("please a profile");
            return;
        }
        var data = editor.get();
        var key1 = parseInt(Math.random() * 1000).toString();
        var key2 = (new Date()).getTime().toString();
        data.channel = key1 + key2;

        myModal.trigger('ee.execute', [data.channel]);
        $('h4.modal-title:first').text(data.sourceFileName + ': execution output...');
        $('#btn_modal').click();
        setTimeout(function () {

            executeByData(data, gearId, profileId);
        }, 800);
    } else
        alert('Please select a data');
});


