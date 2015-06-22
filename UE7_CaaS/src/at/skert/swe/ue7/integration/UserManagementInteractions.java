package at.skert.swe.ue7.integration;

import at.skert.swe.ue7.contracts.data.IRepository;
import at.skert.swe.ue7.contracts.data.User;
import at.skert.swe.ue7.view.dialog.AddUserDialog;
import at.skert.swe.ue7.view.dialog.EditUserDialog;
import at.skert.swe.ue7.view.utils.MessageBox;
import at.skert.swe.ue7.viewmodel.UserManagementViewModel;

public class UserManagementInteractions {
  private UserManagementViewModel viewModel;
  private IRepository<User> userRepository;

  public UserManagementInteractions(IRepository<User> userRepository) {
    this.viewModel = new UserManagementViewModel();
    this.userRepository = userRepository;
  }

  public UserManagementViewModel getIntegratedViewModel() {
    viewModel.setAddUserMethod(() -> showAddUserDialog());
    viewModel.setEditUserMethod(user -> editUser(user));
    viewModel.setDeletUserMethod(user -> deleteUser(user));
    viewModel.setDeactivateUserMethod(user -> deactivateUser(user));
    viewModel.setActivateUserMethod(user -> activateUser(user));
    viewModel.setRefreshUsersMethod(() -> loadUsers());
    viewModel.getUserList().addAll(userRepository.getAll());
    return viewModel;
  }

  public void showAddUserDialog() {
    AddUserDialog dialog = new AddUserDialog();
    dialog
        .setAddUserMethod(user -> userRepository.create(
            user,
            () -> {
              dialog.close();
              viewModel.getUserList().clear();
              viewModel.getUserList().addAll(userRepository.getAll());
            },
            exception -> {
              MessageBox
                  .showErrorDialog(
                      "Fehler",
                      "Es ist ein Fehler beim Hinzuf�gen des Benutzers aufgetreten.",
                      "Beim Versuch den Benutzer zu Speichern ist ein Fehler aufgetreten, der Benutzer konnte nicht gespeichert werden.",
                      exception);
            }));
    dialog.showAndWait();
  }

  public void deleteUser(User user) {
    MessageBox
        .showConfirmDialog(
            "Achtung",
            "Wollen Sie den Benutzer " + user.getUsername()
                + " wirklich l�schen? ",
            () -> {
              userRepository.delete(
                  user,
                  () -> {
                    refreshUsers();
                  },
                  exception -> {
                    MessageBox
                        .showErrorDialog(
                            "Fehler",
                            "Es ist ein Fehler beim Loeschen des Benutzers aufgetreten.",
                            "Der zu loeschende Benutzer wurde nicht in der Datenbank gefunden.",
                            exception);
                  });
            }, () -> {
            });
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
              () -> {
                refreshUsers();
                dialog.close();
              },
              (exception) -> {
                MessageBox
                    .showErrorDialog(
                        "Fehler",
                        "Es ist ein Fehler beim Speichern des Benutzers aufgetreten.",
                        "Beim Versuch den Benutzer zu Speichern ist ein Fehler aufgetreten, der Benutzer konnte nicht gespeichert werden.",
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

  public void loadUsers(){
    viewModel.getUserList().clear();
    viewModel.getUserList().addAll(userRepository.getAll());
  }
  
  public void refreshUsers() {
    viewModel.refreshUses();
  }
}
