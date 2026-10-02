package ru.mirea.elkinasa.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class WebViewFragment extends Fragment {

    private WebView webView;
    private EditText etUrl;
    private Button btnLoad;
    private Button btnBack;
    private Button btnForward;
    private Button btnRefresh;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Загружаем разметку фрагмента
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);

        webView = view.findViewById(R.id.webView);
        etUrl = view.findViewById(R.id.etUrl);
        btnLoad = view.findViewById(R.id.btnLoad);
        btnBack = view.findViewById(R.id.btnBack);
        btnForward = view.findViewById(R.id.btnForward);
        btnRefresh = view.findViewById(R.id.btnRefresh);
        progressBar = view.findViewById(R.id.progressBar);

        // Настройка WebView
        configureWebView();

        // Настройка обработчиков нажатий
        setupButtonListeners();

        // Показываем приветственное сообщение
        Toast.makeText(getContext(), "Добро пожаловать в кошачий браузер!", Toast.LENGTH_SHORT).show();

        return view;
    }

    private void configureWebView() {
        // Получаем настройки WebView
        WebSettings webSettings = webView.getSettings();

        // Включаем поддержку JavaScript
        webSettings.setJavaScriptEnabled(true);

        // Включаем поддержку DOM Storage (необходимо для хранения данных на сайтах)
        webSettings.setDomStorageEnabled(true);

        // Включаем поддержку масштабирования
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false); // Скрываем кнопки зума

        // Настройка загрузки страниц
        webSettings.setLoadWithOverviewMode(true);  // Загружать страницу с обзором
        webSettings.setUseWideViewPort(true);       // Использовать широкий вьюпорт

        // Включаем кэширование
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // Устанавливаем WebViewClient для обработки навигации внутри WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                // Показываем прогресс-бар при начале загрузки страницы
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Скрываем прогресс-бар после загрузки страницы
                progressBar.setVisibility(View.GONE);
                // Обновляем текст в адресной строке
                etUrl.setText(url);
                // Обновляем состояние кнопок навигации
                updateNavigationButtons();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Загружаем URL в текущем WebView, а не открываем в браузере
                view.loadUrl(url);
                return true;
            }
        });

        // Устанавливаем WebChromeClient для отслеживания прогресса загрузки
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // Обновляем прогресс-бар при загрузке страницы
                progressBar.setProgress(newProgress);
            }
        });

        // Загружаем страницу по умолчанию (про котиков)
        webView.loadUrl("https://ru.wikipedia.org/wiki/Кошка");
    }

    //Настройка обработчиков нажатия кнопок
    private void setupButtonListeners() {
        // Кнопка загрузки URL
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etUrl.getText().toString().trim();

                // Если URL не пустой
                if (!url.isEmpty()) {
                    // Если URL не начинается с http:// или https://, добавляем https://
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "https://" + url;
                    }
                    // Загружаем URL в WebView
                    webView.loadUrl(url);
                } else {
                    Toast.makeText(getContext(), "Введите URL адрес!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Кнопка "Назад" - возврат на предыдущую страницу
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                    Toast.makeText(getContext(), "Возвращаемся назад...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Это первая страница, назад нельзя!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Кнопка "Вперед" - переход на следующую страницу
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoForward()) {
                    webView.goForward();
                    Toast.makeText(getContext(), "Идем вперед...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Это последняя страница, вперед нельзя!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Кнопка "Обновить" - перезагрузка текущей страницы
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
                Toast.makeText(getContext(), "Обновляем страничку...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Обновление состояния кнопок навигации (вкл/выкл)
    private void updateNavigationButtons() {
        btnBack.setEnabled(webView.canGoBack());
        btnForward.setEnabled(webView.canGoForward());

        // Меняем прозрачность кнопок, если они неактивны
        btnBack.setAlpha(webView.canGoBack() ? 1.0f : 0.5f);
        btnForward.setAlpha(webView.canGoForward() ? 1.0f : 0.5f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Освобождаем ресурсы WebView при уничтожении фрагмента
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
        }
    }
}