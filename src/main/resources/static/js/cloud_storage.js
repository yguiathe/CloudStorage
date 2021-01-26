// Files features
$('.delete-file').click(function (e) {
    e.preventDefault();
    deleteData(this, "file");
});

// Note features
function registerDeleteNoteListener() {
    $('.delete-note').click(function (e) {
        e.preventDefault();
        deleteData(this, "note");
    });
}

// For opening the note modal
function showNoteModal(noteId, noteTitle, noteDescription) {
    $('#note-id').val(noteId ? noteId : '');
    $('#note-title').val(noteTitle ? noteTitle : '');
    $('#note-description').val(noteDescription ? noteDescription : '');
    $('#noteModal').modal('show');
}

// Credential features
function registerDeleteCredentialListener() {
    $('.delete-credential').click(function (e) {
        e.preventDefault();
        deleteData(this, "credential");
    });
}

// For opening the credentials modal
function showCredentialModal(credentialId, url, username, password) {
    $('#credential-id').val(credentialId ? credentialId : '');
    $('#credential-url').val(url ? url : '');
    $('#credential-username').val(username ? username : '');
    $('#credential-password').val(password ? password : '');
    $('#credentialModal').modal('show');
}

// Load Tab content
$('#nav-tab a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    switch ($(this).attr('id')) {
        case "nav-notes-tab":
            fetchTabContent("note", "#notesList");
            break;
        case "nav-credentials-tab":
            fetchTabContent("credential", "#credentialsList");
            break;
    }
});

// Submit button
$('form > button').not('#logoutBtn')
    .click(
        function(e) {
            e.preventDefault();
            switch ($(this).attr('id')) {
                 case "noteSubmit":
                      postFormData("note", "#notesList");
                      break;
                 case "credentialSubmit":
                      postFormData("credential", "#credentialsList");
                      break;
            }
        }
);

// Generic functions
function displayConfirmation(data) {
    $(".confirmation-alert").html(data);
    $(".confirmation-alert").show();
    setTimeout(
          function() {
               $(".confirmation-alert").fadeOut(1500);
          },
          4000
    );
}

function fetchTabContent(tab, listId) {
    $.get("/" + tab,
        function(data) {
            $(listId).replaceWith(data);
            registerDeleteNoteListener();
            registerDeleteCredentialListener();
        }
    );
}

function postFormData(tab, listId) {
    $.post("/" + tab,
            $("#" + tab + "-form").serialize(),
            function(data) {
                $("#" + tab + "Modal").modal('hide');
                $('.modal-backdrop').remove();
                fetchTabContent(tab, listId);
                displayConfirmation(data);
            }
    );
}

function deleteData(obj, model) {
    var id = $(obj).attr('href');
    $.ajax({
         method: "DELETE",
         url: '/' + model + '/' + id,
         success: function (data) {
                     $("#" + model + "-" + id).remove();
                     displayConfirmation(data);
                  },
         error: function (data) {
                     console.log('Error:', data);
                }
    });
}
