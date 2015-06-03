package at.skert.swe.ue6.integration;

import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.data.User;
import at.skert.swe.ue6.data.UserRepository;
import at.skert.swe.ue6.view.dialog.AddUserDialog;
import at.skert.swe.ue6.view.dialog.EditUserDialog;
import at.skert.swe.ue6.view.utils.MessageBox;
import at.skert.swe.ue6.viewmodel.UserManagementViewModel;

public class UserManagementInteractions {
  UserManagementViewModel viewModel;
  IRepository<User> userRepository;

  public UserManagementInteractions() {
    this.viewModel = new UserManagementViewModel();
    this.userRepository = new UserRepository();
  }

  public UserManagementViewModel getIntegratedViewModel() {
    viewModel.setAddUserMethod(() -> showAddUserDialog());
    viewModel.setEditUserMethod(user -> editUser(user));
    viewModel.setDeletUserMethod(user -> deleteUser(user));
    viewModel.setDeactivateUserMethod(user -> deactivateUser(user));
    viewModel.setActivateUserMethod(user -> activateUser(user));
    viewModel.getUserList().addAll(userRepository.getAll());
    return viewModel;
  }

  public void showAddUserDialog() {
    AddUserDialog dialog = new AddUserDialog();
    dialog.setAddUserMethod(user -> userRepository.create(user, () -> {
      viewModel.getUserList().clear();
      viewModel.getUserList().addAll(userRepository.getAll());
    }, exception -> {
      // TODO: errorhandling
    }));
    dialog.showAndWait();
  }

  public void deleteUser(User user) {
    MessageBox.showConfirmDialog("Achtung", "Wollen Sie den Benutzer " + user.getUsername() + " wirklich löschen? ", () -> {
    userRepository.delete(user, () -> {
      refreshUsers();
    }, exception ->{
      MessageBox
      .showErrorDialog(
          "Fehler",
          "Es ist ein Fehler beim Löschen des Benutzers aufgetreten.",
          "Der zu löschende Benutzer wurde nicht in der Datenbank gefunden.",
          exception);
    });
    }, () -> {});
  }

  public void deactivateUser(User user) {
    user.setActivated(false);
    userRepository
        .update(
            user,
            () -> refreshUsers(),
            exception -> {
              MessageBox
                  .showErrorDialog(
                      "Fehler",
                      "Es ist ein Fehler beim Aktivieren des Benutzers aufgetreten.",
                      "Der zu aktivierende Benutzer wurde nicht in der Datenbank gefunden.",
                      exception);
            });
  }

  public void editUser(User user) {
    showEditUserDialog(user);
    viewModel.getUserList().clear();
    viewModel.getUserList().addAll(userRepository.getAll());
  }

  public void showEditUserDialog(User user) {
    EditUserDialog dialog = new EditUserDialog(user);
    dialog
        .setUpdateUserMethod(x -> {
          userRepository.update(
              user,
              () -> refreshUsers(),
              (exception) -> {
                MessageBox
                    .showErrorDialog(
                        "Fehler",
                        "Es ist ein Fehler beim Aktivieren des Benutzers aufgetreten.",
                        "Der zu aktivierende Benutzer wurde nicht in der Datenbank gefunden.",
                        exception);
              });
        });
    dialog.showAndWait();
  }

  public void activateUser(User user) {
    user.setActivated(true);
    userRepository
        .update(
            user,
            () -> refreshUsers(),
            (exception) -> {
              MessageBox
                  .showErrorDialog(
                      "Fehler",
                      "Es ist ein Fehler beim Aktivieren des Benutzers aufgetreten.",
                      "Der zu aktivierende Benutzer wurde nicht in der Datenbank gefunden.",
                      exception);
            });
  }

  public void refreshUsers() {
    viewModel.getUserList().clear();
    viewModel.getUserList().addAll(userRepository.getAll());
  }

}
