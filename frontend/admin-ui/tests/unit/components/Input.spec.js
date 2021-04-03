import { render } from '@testing-library/vue';

import LabelInput from '../components/Input.vue';

test('show label and placeholder', async () => {
  const { getByText, getByPlaceholderText } = render(LabelInput, {
    slots: {
      default: 'Label Name',
    },
    props: {
      placeholder: 'Placeholder Name',
    },
  });
  getByText('Label Name');
  getByPlaceholderText('Placeholder Name');
});
