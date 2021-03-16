module.exports = {
  presets: ['@vue/cli-plugin-babel/preset'],
  plugins: [
    [
      'babel-plugin-tsconfig-paths',
      {
        relative: true,
        extensions: ['.js', '.jsx', '.ts', '.tsx', '.es', '.es6', '.mjs'],
        rootDir: '.',
        transformFunctions: [
          'require',
          'require.resolve',
          'System.import',
          'jest.genMockFromModule',
          'jest.mock',
          'jest.unmock',
          'jest.doMock',
          'jest.dontMock',
          'jest.setMock',
          'require.requireActual',
          'require.requireMock',
        ],
      },
    ],
  ],
};
