package com.joaobembe.carteiraru;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CardapioFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ViewPager2 viewPager2;
    private String mParam1;
    private String mParam2;

    public CardapioFragment() {
        // Required empty public constructor
    }

    public static CardapioFragment newInstance(String param1, String param2) {
        CardapioFragment fragment = new CardapioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cardapio, container, false);

        viewPager2 = rootView.findViewById(R.id.viewPagerCardapio);

        List<SliderItem> sliderItems = new ArrayList<>();
        List<String> pratoPrincipal = new ArrayList<>();
        List<String> segundaOpcao = new ArrayList<>();
        List<String> acompanhamento = new ArrayList<>();
        List<String> guanicao = new ArrayList<>();
        List<String> sobremesa = new ArrayList<>();
        List<String> suco = new ArrayList<>();

        pratoPrincipal.add("Arroz, feijão e bife");
        segundaOpcao.add("Macarrão com molho de queijo");

        acompanhamento.add("Salada verde");
        acompanhamento.add("Purê de batatas");
        acompanhamento.add("Legumes refogados");

        guanicao.add("Farofa");
        guanicao.add("Vinagrete");

        sobremesa.add("Pudim de leite");
        sobremesa.add("Sorvete de chocolate");
        sobremesa.add("Torta de limão");

        suco.add("Suco de laranja");
        suco.add("Suco de maracujá");
        suco.add("Suco de melancia");

        SliderItem sliderItem1 = new SliderItem(pratoPrincipal, segundaOpcao,acompanhamento,guanicao,sobremesa,suco);
        sliderItems.add(sliderItem1);

        List<String> mainDish = new ArrayList<>();
        List<String> alternativeOption = new ArrayList<>();
        List<String> sideDish = new ArrayList<>();
        List<String> garnish = new ArrayList<>();
        List<String> dessert = new ArrayList<>();
        List<String> juice = new ArrayList<>();

        mainDish.add("Grilled chicken with rice and vegetables");
        alternativeOption.add("Pasta with tomato sauce and cheese");

        sideDish.add("Mixed green salad");
        sideDish.add("Mashed potatoes");
        sideDish.add("Stir-fried vegetables");

        garnish.add("Toasted breadcrumbs");
        garnish.add("Vinaigrette sauce");

        dessert.add("Creamy caramel flan");
        dessert.add("Chocolate ice cream");
        dessert.add("Zesty lemon tart");

        juice.add("Freshly squeezed orange juice");
        juice.add("Passion fruit juice");
        juice.add("Refreshing watermelon juice");

        sliderItem1 = new SliderItem(mainDish, alternativeOption, sideDish, garnish, dessert, juice);
        sliderItems.add(sliderItem1);

        viewPager2.setAdapter(new SliderAdapter(sliderItems));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        TextView tvDiaDaSemana = rootView.findViewById(R.id.tvDiaDaSemana);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    tvDiaDaSemana.setText("Segunda-Feira");
                }
                if (position == 1) {
                    tvDiaDaSemana.setText("Terça-Feira");
                }

            }
        });

        return rootView;
    }
}