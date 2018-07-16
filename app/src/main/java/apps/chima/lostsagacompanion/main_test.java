package apps.chima.lostsagacompanion;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

public class main_test extends FragmentActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {
    private Fragment frag;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFragment(new HeroFragment());

        BottomNavigationView bottom_bar = findViewById(R.id.bottom_navigation_bar);
      bottom_bar.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.hero_tab:
                frag = new HeroFragment();
                break;
            case R.id.costume_tab:
                frag = new CostumeFragment();
                break;
            case R.id.evo_tab:
                frag = new EvoFragment();
                break;
            case R.id.gear_tab:
                frag = new GearFragment();
                break;
            case R.id.guide_tab:
                frag = new GuideFragment();
                break;
        }
        setFragment(frag);
        return true;
    }

    public void setFragment(Fragment fragment){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment).commit();
    }
}
