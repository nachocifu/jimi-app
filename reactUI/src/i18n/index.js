import i18next from 'i18next';
import en from './en';
import es from './es';
import LngDetector from 'i18next-browser-languagedetector';

i18next
  .use(LngDetector)
  .init({
    interpolation: {
      // React already does escaping
      escapeValue: false,
    },
    // lng: 'es',//window.navigator.language?window.navigator.language:'en', // 'en' | 'es'
    // fallbackLng: "en",
    // Using simple hardcoded resources for simple example
    resources: {
      es: {translation: es},
      en: {translation: en},
    },
  });

export default i18next;
