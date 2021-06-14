package com.example.studioghibliapi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.concurrent.atomic.AtomicBoolean;

/*
        - Criar uma nova classe Java
        - Implementar a herança de um elemento ou diretamente da classe View
        - No exemplo será uma Edit Text alterada com o X para limpar o texto
        - Após definir a herança implementar todos os contrutores necessário/ sugeridos
        - O preview será exibido
        - Na pasta drawables adicionar o icone X ( drawable -> add vector -> cliparte --> selecionar a forma)
        - Adicionar um icone preto e outro cinza ( opacidade 50%)
        - Define a variável de acesso ao icone
        - Cria um helper que instancia o icone - init()
        - Chama o helper nos contrutores
        - Se herdar da view prcisa desenhar a interface, sobrescrevendo o método onDraw
        - Quando herda de uma subclasse é possivel apenas sobrescrever para customizar
   */
public class CustomView extends AppCompatActivity {

    //define o drawable q será instanciado
    Drawable mClearButtonImage;

    //construtores obrigatórios da classe
    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context,
                             AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //metodo para inicialização do componente
    private void init() {
        // Inicializa o Drawable
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_clear_opaque_24, null);

        // define a ação do clique do botãoclique do botão.
        setOnTouchListener(new View.OnTouchListener() {


            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // getCompoundDrawables()[2] :retorna um array de Drawables contendo start, top, end e bottom
                // Se o Drawabale estiver no final do texto: [2].

                if ((getCompoundDrawablesRelative()[2] != null)) {
                    float clearButtonStart; // Linguagens LTR
                    float clearButtonEnd;  //Linguagens RTL
                    //um boolean que pode ser atualizado dinamicamente
                    AtomicBoolean isClearButtonClicked = new AtomicBoolean(false);
                    // detecta a direção do toque
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        // Se RTL, cola o botão na esquerda
                        clearButtonEnd = mClearButtonImage
                                .getIntrinsicWidth() + getPaddingStart();
                        // se o toque ocorrer antes do fim do botão
                        //  isClearButtonClicked definido como true.
                        if (event.getX() < clearButtonEnd) {
                            isClearButtonClicked.set(true);
                        }
                    } else {
                        // Se o layout é LTR.

                        clearButtonStart = (getWidth() - getPaddingEnd()
                                - mClearButtonImage.getIntrinsicWidth());
                        // Se o toque ocorrer depois do inicio do botão
                        // isClearButtonClicked = true.
                        if (event.getX() > clearButtonStart) {
                            isClearButtonClicked.set(true);
                        }

                        // verifica o clique do botão
                        if (isClearButtonClicked.get()) {
                            // Verifica o ACTION_DOWN (sempre ocorre antes do ACTION_UP).
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                // troca a versão do botão
                                mClearButtonImage =
                                        ResourcesCompat.getDrawable(getResources(),
                                                R.drawable.ic_baseline_clear_24, null);
                                showClearButton();
                            }
                            // Verifica o  ACTION_UP.
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                // Troca pela versão opaca
                                mClearButtonImage =
                                        ResourcesCompat.getDrawable(getResources(),
                                                R.drawable.ic_baseline_clear_opaque_24, null);
                                // limpa o texto
                                getText().clear();
                                //esconde o botão
                                hideClearButton();
                                return true;
                            }
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }
        });

        // Se o texto muda mostra/oculta o botão
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start, int count, int after) {
                // Do nothing.
            }

            @Override
            public void onTextChanged(CharSequence s,
                                      int start, int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing.
            }
        });
    }

    //exibição do botão
    private void showClearButton() {
        // Define  aposição do drawable
        //exige versão minima do sdk
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelativeWithIntrinsicBounds
                    (null,                      // Inicio do texto
                            null,               // Topo do texto.
                            mClearButtonImage,  // Fim do Texto
                            null);              // Abaixo do texto
        }
    }

    /**
     * oculta o botão
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void hideClearButton() {

        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,             // Inicio do texto
                        null,      // Topo do texto
                        null,      // Fim do texto
                        null);     // Abaixo do texto.
    }

}
