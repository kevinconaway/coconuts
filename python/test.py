import unittest

suite = unittest.TestLoader().discover('test', pattern='*.py')
unittest.TextTestRunner(verbosity=2).run(suite)