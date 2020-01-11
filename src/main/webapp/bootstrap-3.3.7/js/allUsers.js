$(document).ready(function () {
  getTable();
  console.log("ПРИВЕТ!!!!!!!!!!!!!!!!");
});

//modal form
$(document).on('click', '#editUserBtn', function () {

  $("#updateUserId").val($(this).closest("tr").find("#tableId").text());
  $("#updateUserId").prop("disabled", true);

  $("#updateUserLogin").val($(this).closest("tr").find("#tableLogin").text());

  //$("#updateUserPass").hide();
  $("#updateUserPass").val($(this).closest("tr").find("#tablePass").text());

  $("#updateUserName").val($(this).closest("tr").find("#tableName").text());

  $("#updateUserEmail").val($(this).closest("tr").find("#tableAddress").text());

  let role = $(this).closest("tr").find("#tableRole").text();
  // let admin = "ADMIN";
  if (role === "ADMIN") {
    $('#updateUserRole option:contains("ADMIN")').prop("selected", true);
  } else {
    $('#updateUserRole option:contains("USER")').prop("selected", true);
  }
});


//addForm
$("#addFormUser").click(function (event) {
  event.preventDefault();
  addForm();
  $('#addLogin').val('');
  $('#addPassword').val('');
  $('#addUserName').val('');
  $('#addMail').val('');
});

$("#resetTable").click(function () {
  getTable();
});

function addForm() {

  let user = {
    'login': $("#addLogin").val(),
    'password': $("#addPassword").val(),
    'username': $("#addUserName").val(),
    'mail': $("#addMail").val(),
    'roles': $("#addRole").val()
  };

  $.ajax({

    type: 'POST',
    url: "/restapi/",

    contentType: 'application/json;',
    data: JSON.stringify(user),
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    async: true,
    dataType: 'JSON',
    success: function () {
      location.href = "#tab1";
      getTable()
    }
  });
}

//updateForm
$("#updateFormUser").click(function (event) {
  event.preventDefault();
  updateForm();
  $("#editUser").modal('toggle');
  getTable();
});

function updateForm() {
  let user = {
    'id': $("#updateUserId").val(),
    'login': $("#updateUserLogin").val(),
    'password': $("#updateUserPass").val(),
    'username': $("#updateUserName").val(),
    'mail': $("#updateUserEmail").val(),
    'roles': $("#updateUserRole").val()
  };

  $.ajax({

    type: 'Put',
    url: "/restapi/",

    contentType: 'application/json;',
    data: JSON.stringify(user),

    success: function () {
      getTable();
    }
  });
}

//deleteForm
$(document).on('click', '#deleteUser', function () {
  deleteUserId = $(this).closest("tr").find("#tableId").text();
  deleteUser(deleteUserId);
});

function deleteUser(id) {

  $.ajax({
    type: 'DELETE',
    url: "/restapi/" + id,

    contentType: 'application/json;',
    data: JSON.stringify(id),
    success: function () {
      getTable();
    }
  });
}


function getTable() {
  $.ajax({
    type: 'GET',
    url: "/restapi/allUsers/",
    contentType: 'application/json;',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
    dataType: 'JSON',
    success: function (listUsers) {
      let htmlTable = "";
      for (let i = 0; i < listUsers.length; i++) {

        htmlTable += `<tr id="list" valign="center">
                        <td id="tableId" align="center">${listUsers[i].id}</td>
                        <td id="tableLogin"  align="center">${listUsers[i].login}</td>
                        <td id="tableName"  align="center">${listUsers[i].username}</td>
                        <td id="tableAddress" align="center">${listUsers[i].mail}</td>
                        <td id="tableRole" align="center">${listUsers[i].roles}</td>
                        <td align="center"><button id="editUserBtn"  class="btn btn-primary" type="button" data-toggle="modal" data-target="#editUser">Редактировать</button></td>
                        <td align="center"><button id="deleteUser" class="btn btn-danger" type="button" data-target="#deleteUser"> Удалить!</button></td>
                        </tr><br>`;
      }
      $("#tableUser #list").remove();
      $("#tableUser thead").after(htmlTable);
    }
  })
}
