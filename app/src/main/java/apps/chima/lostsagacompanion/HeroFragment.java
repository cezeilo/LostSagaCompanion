package apps.chima.lostsagacompanion;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HeroFragment extends Fragment {


    private ArrayList<Hero> heroList;
    private HeroAdapter ha;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        heroList = new ArrayList<>();


        View v = inflater.inflate(R.layout.hero_fragment_layout, null);


        //Spinner = Hero Types
        // Spinner 2 = Hero Categories
        Spinner spinner = v.findViewById(R.id.hero_selector);
        Spinner spinner2 = v.findViewById(R.id.hero_categories_selector);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.hero_types,
                android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.hero_categories,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);

        spinner.setSelection(0);
        spinner2.setSelection(0);




        //Create array list for all heroes
        createList(heroList);

        //Initialize List
        ListView lv = v.findViewById(R.id.HeroList);
         ha = new HeroAdapter(getContext(), R.layout.hero_list_layout, new ArrayList<>(heroList));
        lv.setAdapter(ha);


        //Sets the image icons for all heroes
        createIcons(heroList);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = parent.getItemAtPosition(position).toString();

                switch(selected){

                    case ("Melee"):
                        ArrayList<Hero> meleeHeroes = new ArrayList<>();
                        for(Hero hero: heroList){
                            if(hero.getCombatStyle().trim().equals("Melee")){
                                meleeHeroes.add(hero);
                            }
                        }



                        ha.clear();
                        //Toast.makeText(parent.getContext(), "Melee List: " + Integer.toString(meleeHeroes.size()), Toast.LENGTH_LONG).show();
                        ha.addAll(meleeHeroes);
                        break;


                    case ("Magic"):
                        ArrayList<Hero> magicHeroes = new ArrayList<>();
                        for(Hero hero: heroList){
                            System.out.println(hero.getName());
                            if(hero.getCombatStyle().trim().equals("Magic")){
                                magicHeroes.add(hero);
                            }
                        }
                        //Problem: Clear removes everything in the ARRAY LIST IT HOLDS (so heroList)
                        //Maybe loop with .remove() ?




                        ha.clear();
                        //Toast.makeText(parent.getContext(),"Magic List: " + Integer.toString(magicHeroes.size()), Toast.LENGTH_LONG).show();
                        ha.addAll(magicHeroes);
                        break;


                    case("Special"):
                        ArrayList<Hero> specialHeroes = new ArrayList<>();
                        for(Hero hero: heroList){
                            if(hero.getCombatStyle().trim().equals("Special")){
                                specialHeroes.add(hero);
                            }
                        }



                        ha.clear();
                        //Toast.makeText(parent.getContext(),"Special List: " + Integer.toString(specialHeroes.size()), Toast.LENGTH_LONG).show();
                        ha.addAll(specialHeroes);
                        break;

                    case("Ranged"):
                        ArrayList<Hero> rangedHeroes = new ArrayList<>();
                        for(Hero hero: heroList){
                            if(hero.getCombatStyle().trim().equals("Ranged")){
                                rangedHeroes.add(hero);
                            }
                        }


                        ha.clear();

                        //Toast.makeText(parent.getContext(), "Ranged List:" + Integer.toString(rangedHeroes.size()), Toast.LENGTH_LONG).show();
                        ha.addAll(rangedHeroes);
                        break;




                    case("All"):

                        ha.clear();
                        //Toast.makeText(parent.getContext(), "Hero List:" + Integer.toString(heroList.size()), Toast.LENGTH_LONG).show();
                        ha.addAll(heroList);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;
    }



    public void createList(ArrayList<Hero> list){
        Resources myRes = getResources();
        InputStream myFile = myRes.openRawResource(R.raw.hero_list);
        try {
          InputStreamReader inputReader = new InputStreamReader(myFile, "utf-8");
            BufferedReader buffReader = new BufferedReader(inputReader);

            String line = null;
            while((line = buffReader.readLine()) != null) {
                if(line.equals("")){
                    break;
                }

               String[] c = line.split(",");
                int heroID = Integer.parseInt(c[0]);
                String heroName = c[1];
                String combatStyle = c[2];
                String category = c[3];

                Hero hero = new Hero(heroID, heroName, combatStyle, category, getContext());
                list.add(hero);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public void createIcons(ArrayList<Hero> list){
        AssetManager assets = getContext().getAssets();


        try {

            for(int counter = 0; counter < list.size(); counter++){
                int num = heroList.get(counter).getId();
                InputStream is = assets.open("heroes/icons/hero_" + num + "_icon.jpg");
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                list.get(counter).setIconBitmap(bitmap);
            }
        }

        catch(IOException e){
            e.printStackTrace();
        }

    }

}
