module.exports = {
  purge: { content: ['./public/**/*.html', './src/**/*.vue'] },
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {
      transitionProperty: {},
      minWidth: {
        20: '5rem',
        18: '4.5rem',
        16: '4rem',
        xs: '20rem',
        sm: '24rem',
        md: '28rem',
        lg: '32rem',
        xl: '36rem',
        '2xl': '42rem',
      },
      minHeight: {
        14: '3.5rem',
        24: '6rem',
        96: '24rem',
      },
    },
    screens: {
      sm: '640px',
      // => @media (min-width: 640px) { ... }

      md: '768px',
      // => @media (min-width: 768px) { ... }

      lg: '1024px',
      // => @media (min-width: 1024px) { ... }
      xl: '1280px',
      // => @media (min-width: 1280px) { ... }

      '2xl': '1536px',
      // => @media (min-width: 1536px) { ... }
      'hover-hover': { raw: '(hover: hover)' },
    },
  },
  variants: {
    extend: {
      transitionProperty: ['hover'],
      width: ['hover'],
      padding: ['hover'],
    },
  },
};
