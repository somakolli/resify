module.exports = {
  env: {
    browser: true,
  },
  extends: [
    'eslint:recommended'
  ],
  root: true,
  parser: "@typescript-eslint/parser",
  plugins: ["@typescript-eslint"],
  rules: {
    "no-unused-vars": "warn"
  }
};
