package tripleh.lmh.farmerguideadmin.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import tripleh.lmh.farmerguideadmin.R;
import tripleh.lmh.farmerguideadmin.dialog.AdminDialog;
import tripleh.lmh.farmerguideadmin.fragment.AdminNewsFragment;
import tripleh.lmh.farmerguideadmin.fragment.ChangePasswordFragment;
import tripleh.lmh.farmerguideadmin.fragment.ConversationFragment;
import tripleh.lmh.farmerguideadmin.sharepref.SharePrefHelper;


public class AdminActivity extends AppCompatActivity {
    public static NavigationView navView;
    private DrawerLayout drawerLayout;
    private SharePrefHelper sharePrefHelper;
    private FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    class NavigationListener implements OnNavigationItemSelectedListener {
        NavigationListener() {
        }

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            for (int i = 0; i < AdminActivity.navView.getMenu().size(); i++) {
                AdminActivity.navView.getMenu().getItem(i).setChecked(false);
            }
            item.setChecked(true);
            AdminActivity.this.drawerLayout.closeDrawers();
            FragmentTransaction changTransaction;
            switch (item.getItemId()) {
                case R.id.change_pass:
                    AdminActivity.this.setTitle("Change Password");
                    changTransaction = AdminActivity.this.getSupportFragmentManager().beginTransaction();
                    changTransaction.replace(R.id.admin_pager, new ChangePasswordFragment());
                    changTransaction.commit();
                    break;
                case R.id.log_out:
                    AdminActivity.this.LogOut();
                    break;
                case R.id.nav_conversation:
                    AdminActivity.this.setTitle("Conversation");
                    changTransaction = AdminActivity.this.getSupportFragmentManager().beginTransaction();
                    changTransaction.replace(R.id.admin_pager, new ConversationFragment(AdminActivity.this.getApplicationContext()));
                    changTransaction.commit();
                    break;
                case R.id.nav_post:
                    AdminActivity.this.setTitle("Post");
                    changTransaction = AdminActivity.this.getSupportFragmentManager().beginTransaction();
                    changTransaction.replace(R.id.admin_pager, new AdminNewsFragment());
                    changTransaction.commit();
                    break;
                default:
                    break;
            }
            return true;
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        this.sharePrefHelper = new SharePrefHelper(getApplicationContext());
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("Conversation");
        setTitleColor(-1);
        this.transaction.replace(R.id.admin_pager, new ConversationFragment(getApplicationContext()));
        this.transaction.disallowAddToBackStack();
        this.transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator((int) R.drawable.ic_menu_black_24dp);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationListener());
        this.drawerLayout.addDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void LogOut() {
        new AdminDialog.DialogBuilder().Context(this).message("Are you sure you want to log out")
                .txtOkColor(R.color.yso_color).
                Result(new AdminDialog.OnDialogResult() {
                    @Override
                    public void finish() {
                         sharePrefHelper.setAdminLogout();
                         System.exit(0);
                    }
                }).
                Build().
                show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        this.drawerLayout.openDrawer((int) GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.drawerLayout.isDrawerOpen( GravityCompat.START)) {
            this.drawerLayout.closeDrawers();
        }
    }
}
